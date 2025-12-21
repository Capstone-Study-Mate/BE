package com.example.study_mate.study.service;

import com.example.study_mate.global.common.PageResponse;
import com.example.study_mate.global.exception.code.BusinessException;
import com.example.study_mate.member.domain.Member;
import com.example.study_mate.member.repository.MemberRepository;
import com.example.study_mate.memberpreference.domain.MemberPreference;
import com.example.study_mate.memberpreference.enums.ActivityDay;
import com.example.study_mate.memberpreference.enums.ActivityTime;
import com.example.study_mate.memberpreference.repository.MemberPreferenceRepository;
import com.example.study_mate.study.domain.Study;
import com.example.study_mate.study.dto.req.StudyCreateRequest;
import com.example.study_mate.study.dto.res.MyStudyResponse;
import com.example.study_mate.study.dto.res.StudyCreateResponse;
import com.example.study_mate.study.dto.res.StudyDetailResponse;
import com.example.study_mate.study.dto.res.StudyListResponse;
import com.example.study_mate.study.repository.StudyRepository;
import com.example.study_mate.studyapplication.domain.StudyApplication;
import com.example.study_mate.studyapplication.enums.ApplicationStatus;
import com.example.study_mate.studyapplication.repository.StudyApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.example.study_mate.global.exception.code.GeneralErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyService {


    private final StudyRepository studyRepository;
    private final MemberRepository memberRepository;
    private final MemberPreferenceRepository memberPreferenceRepository;
    private final StudyApplicationRepository studyApplicationRepository;


    // 스터디 그룹 생성 비즈니스 로직
    public StudyCreateResponse createStudy(Long memberId, StudyCreateRequest request) {

        Member leader = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(USER_NOT_FOUND));

        // 해당 유저의 대학 인증이 되지 않으면 예외 처리
        if (leader.getUniversity() == null) {
            throw new BusinessException(FORBIDDEN);
        }

        // 최대 인원 수를 알맞지 않은 값으로 했을 경우 예외 처리
        if (request.maxMembers() < 1) {
            throw new BusinessException(INVALID_INPUT_VALUE);
        }

        Study study = Study.builder()
                .title(request.title())
                .description(request.description())
                .leader(leader)
                .university(leader.getUniversity())
                .maxMembers(request.maxMembers())
                .currentMembers(1)
                .studyPurpose(request.studyPurpose())
                .tendency(request.tendency())
                .interest(request.interest())
                .activityTimes(request.activityTimes())
                .activityDays(request.activityDays())
                .build();

        studyRepository.save(study);

        return new StudyCreateResponse(study.getId(), study.getTitle());
    }


    // 모든 스터디 그룹 조회 비즈니스 로직
    @Transactional(readOnly = true)
    public PageResponse<StudyListResponse> getStudies(int page, int size) {

        // 페이징 구조 설정 ( 데이터의 양이 많을 경우를 대비하여 페이지화 )
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "updatedAt")
        );

        Page<Study> pageResult = studyRepository.findAll(pageable);

        // 모든 스터디 그룹을 페이징 구조에 맞춰 반환
        return PageResponse.of(
                pageResult,
                StudyListResponse::from
        );
    }

    // 상세 조회 기능
    @Transactional(readOnly = true)
    public StudyDetailResponse getStudyDetail(Long studyId) {

        Study study = studyRepository.findById(studyId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND));

        // LAZY 컬렉션(activityTimes/activityDays)을 트랜잭션 안에서 미리 로딩해서
        // JSON 직렬화 시점(no session) LazyInitializationException을 방지한다.
        study.getActivityTimes().size();
        study.getActivityDays().size();

        return StudyDetailResponse.from(study);
    }


    // 유저가 속한 스터디 그룹 조회 기능
    @Transactional(readOnly = true)
    public PageResponse<MyStudyResponse> getMyStudies(
            Long memberId,
            Pageable pageable
    ) {
        Page<Study> leaderPage =
                studyRepository.findMyLeaderStudies(memberId, pageable);

        Page<StudyApplication> applicationPage =
                studyApplicationRepository.findByMember_IdAndStatus(
                        memberId,
                        ApplicationStatus.APPROVED,
                        pageable
                );

        Set<Study> mergedStudies = new LinkedHashSet<>();
        mergedStudies.addAll(leaderPage.getContent());
        mergedStudies.addAll(
                applicationPage.getContent()
                        .stream()
                        .map(StudyApplication::getStudy)
                        .toList()
        );

        return new PageResponse<>(
                mergedStudies.stream()
                        .map(MyStudyResponse::from)
                        .toList(),
                pageable.getPageNumber(),
                pageable.getPageSize(),
                mergedStudies.size(),
                1,
                true,
                true
        );
    }


    // 해당 유저의 성향에 맞춰 스터드 그룹 추천 기능
    public PageResponse<StudyListResponse> getRecommendStudies(Member member,int page, int size) {

        MemberPreference pref = memberPreferenceRepository.getReferenceById(member.getId());

        List<Study> studies = studyRepository.findAll();

        // 리스트에 해당 스터디 그룹의 성향과 유저의 성향을 비교하는 메서드 적용
        List<ScoredStudy> scoredStudies = new ArrayList<>(studies.stream().
                map(study -> new ScoredStudy(study, calculateScore(pref, study)))
                .toList());

        // 가중치를 기준으로 내림차순 정렬
        scoredStudies.sort((a,b) -> b.score() - a.score());


        Pageable pageable = PageRequest.of(page, size);

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), scoredStudies.size());

        if (start > scoredStudies.size())
            start = scoredStudies.size();

        if (end < start)
            end = start;

        List<Study> pagedStudies = scoredStudies.subList(start, end).stream()
                .map(ScoredStudy::study)
                .toList();

        Page<Study> recommends = new PageImpl<>(pagedStudies, pageable, scoredStudies.size());

        return PageResponse.of(recommends, StudyListResponse::from);

    }


    // 추천 알고리즘 ( 관심사 기반 점수 부여 시스템 )
    public int calculateScore(MemberPreference pref, Study study) {
        int score = 0;

        // 목적 일치
        if (pref.getStudyPurpose() == study.getStudyPurpose()) {
            score += 40;
        }

        // 성향 일치
        if (pref.getTendency() == study.getTendency()) {
            score += 20;
        }

        // 관심사 부분 매칭
        if (study.getInterest() != null &&
                study.getInterest().toLowerCase().contains(pref.getInterest().toLowerCase())) {
            score += 15;
        }

        // 활동 시간 교집합
        if (pref.getActivityTimes() != null && study.getActivityTimes() != null) {
            Set<ActivityTime> timeIntersect = new HashSet<>(pref.getActivityTimes());
            timeIntersect.retainAll(study.getActivityTimes());
            score += timeIntersect.size() * 10;
        }

        // 활동 요일 교집합
        if (pref.getActivityDays() != null && study.getActivityDays() != null) {
            Set<ActivityDay> dayIntersect = new HashSet<>(pref.getActivityDays());
            dayIntersect.retainAll(study.getActivityDays());
            score += dayIntersect.size() * 5;
        }

        return score;
    }
}
