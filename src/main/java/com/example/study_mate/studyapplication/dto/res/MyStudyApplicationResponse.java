package com.example.study_mate.studyapplication.dto.res;

import com.example.study_mate.studyapplication.domain.StudyApplication;
import com.example.study_mate.studyapplication.enums.ApplicationStatus;

import java.time.LocalDateTime;

public record MyStudyApplicationResponse(
        Long applicationId,
        Long studyId,
        String studyTitle,
        String universityName,
        ApplicationStatus status,
        LocalDateTime appliedAt
) {
    public static MyStudyApplicationResponse from(StudyApplication application) {
        return new MyStudyApplicationResponse(
                application.getId(),
                application.getStudy().getId(),
                application.getStudy().getTitle(),
                application.getStudy().getUniversity().getName(),
                application.getStatus(),
                application.getCreatedAt()
        );
    }
}
