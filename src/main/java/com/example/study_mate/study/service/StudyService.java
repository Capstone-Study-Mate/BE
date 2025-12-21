package com.example.study_mate.study.service;

import com.example.study_mate.global.common.PageResponse;
import com.example.study_mate.global.exception.code.BusinessException;
import com.example.study_mate.member.domain.Member;
import com.example.study_mate.member.repository.MemberRepository;
import com.example.study_mate.study.domain.Study;
import com.example.study_mate.study.dto.req.StudyCreateRequest;
import com.example.study_mate.study.dto.res.StudyCreateResponse;
import com.example.study_mate.study.dto.res.StudyDetailResponse;
import com.example.study_mate.study.dto.res.StudyListResponse;
import com.example.study_mate.study.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.study_mate.global.exception.code.GeneralErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyService {
    private final StudyRepository studyRepository;
    private final MemberRepository memberRepository;

    public StudyCreateResponse createStudy(Long memberId, StudyCreateRequest request) {
        Member leader = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(USER_NOT_FOUND));


        if (leader.getUniversity() == null) {
            throw new BusinessException(FORBIDDEN);
        }

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
                .activityTimes(request.activityTimes())
                .activityDays(request.activityDays())
                .build();
        studyRepository.save(study);

        return new StudyCreateResponse(study.getId(), study.getTitle());
    }

    @Transactional(readOnly = true)
    public PageResponse<StudyListResponse> getStudies(int page, int size) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        Page<Study> pageResult = studyRepository.findAll(pageable);

        return PageResponse.of(
                pageResult,
                StudyListResponse::from
        );
    }

    // 상세 조회
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

}
