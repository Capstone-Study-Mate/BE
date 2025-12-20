package com.example.study_mate.studyapplication.converter;

import com.example.study_mate.studyapplication.domain.StudyApplication;
import com.example.study_mate.studyapplication.dto.res.StudyApplicationResponse;
import org.springframework.stereotype.Component;

@Component
public class StudyApplicationConverter {

    public StudyApplicationResponse toResponse(StudyApplication application) {
        return new StudyApplicationResponse(
                application.getId(),
                application.getStudy().getId(),
                application.getMember().getId(),
                application.getStatus()
        );
    }
}
