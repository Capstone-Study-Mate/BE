package com.example.study_mate.studyapplication.service;

import com.example.study_mate.global.exception.code.BusinessException;
import com.example.study_mate.member.domain.Member;
import com.example.study_mate.member.repository.MemberRepository;
import com.example.study_mate.study.domain.Study;
import com.example.study_mate.study.repository.StudyRepository;
import com.example.study_mate.studyapplication.converter.StudyApplicationConverter;
import com.example.study_mate.studyapplication.domain.StudyApplication;
import com.example.study_mate.studyapplication.dto.res.StudyApplicationResponse;
import com.example.study_mate.studyapplication.enums.ApplicationStatus;
import com.example.study_mate.studyapplication.repository.StudyApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.study_mate.global.exception.code.GeneralErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyApplicationService {

    private final StudyApplicationRepository applicationRepository;
    private final StudyRepository studyRepository;
    private final MemberRepository memberRepository;
    private final StudyApplicationConverter converter;

    @Transactional
    public StudyApplicationResponse apply(Long memberId, Long studyId) {

        Study study = studyRepository.findById(studyId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(USER_NOT_FOUND));

        // 리더는 신청 불가
        if (study.isLeader(memberId)) {
            throw new BusinessException(INVALID_STATE);
        }

        // 정원 초과
        if (study.isFull()) {
            throw new BusinessException(INVALID_STATE);
        }

        // 중복 신청 방지
        if (applicationRepository.existsByStudyIdAndMemberId(studyId, memberId)) {
            throw new BusinessException(INVALID_STATE);
        }

        StudyApplication application = applicationRepository.save(
                StudyApplication.builder()
                        .study(study)
                        .member(member)
                        .status(ApplicationStatus.PENDING)
                        .build()
        );
        return converter.toResponse(application);
    }

    public StudyApplicationResponse approveApplication(Long leaderId, Long applicationId) {
        StudyApplication application =
                applicationRepository.findById(applicationId)
                        .orElseThrow(() -> new BusinessException(NOT_FOUND));

        Study study = application.getStudy();

        // 리더 권한 체크
        if (!study.isLeader(leaderId)) {
            throw new BusinessException(FORBIDDEN);
        }

        // 이미 처리된 신청 방지
        if (!application.isPending()) {
            throw new BusinessException(INVALID_STATE);
        }

        // 정원 초과 방지
        if (study.isFull()) {
            throw new BusinessException(INVALID_STATE);
        }

        application.approve();
        study.increaseMembers();

        return converter.toResponse(application);
    }

    public StudyApplicationResponse rejectApplication(Long leaderId, Long applicationId) {

        StudyApplication application =
                applicationRepository.findById(applicationId)
                        .orElseThrow(() ->
                                new BusinessException(NOT_FOUND)
                        );

        Study study = application.getStudy();

        if (!study.isLeader(leaderId)) {
            throw new BusinessException(FORBIDDEN);
        }

        application.reject();

        return converter.toResponse(application);
    }
}
