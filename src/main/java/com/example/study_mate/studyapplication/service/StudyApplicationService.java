package com.example.study_mate.studyapplication.service;

import com.example.study_mate.global.exception.code.BusinessException;
import com.example.study_mate.study.domain.Study;
import com.example.study_mate.studyapplication.domain.StudyApplication;
import com.example.study_mate.studyapplication.repository.StudyApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.study_mate.global.exception.code.GeneralErrorCode.FORBIDDEN;
import static com.example.study_mate.global.exception.code.GeneralErrorCode.NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyApplicationService {

    private final StudyApplicationRepository applicationRepository;

    public void approveApplication(Long leaderId, Long applicationId) {

        StudyApplication application =
                applicationRepository.findById(applicationId)
                        .orElseThrow(() ->
                                new BusinessException(NOT_FOUND)
                        );

        Study study = application.getStudy();

        // 리더만 승인 가능
        if (!study.isLeader(leaderId)) {
            throw new BusinessException(FORBIDDEN);
        }

        application.approve();
    }

    public void rejectApplication(Long leaderId, Long applicationId) {

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
    }
}
