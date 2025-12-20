package com.example.study_mate.studyapplication.dto.res;

import com.example.study_mate.studyapplication.enums.ApplicationStatus;

public record StudyApplicationResponse(
        Long applicationId,
        Long studyId,
        Long memberId,
        ApplicationStatus status
) {}
