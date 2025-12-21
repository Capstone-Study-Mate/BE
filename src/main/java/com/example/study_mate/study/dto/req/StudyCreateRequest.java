package com.example.study_mate.study.dto.req;

import com.example.study_mate.memberpreference.enums.ActivityDay;
import com.example.study_mate.memberpreference.enums.ActivityTime;
import com.example.study_mate.memberpreference.enums.StudyPurpose;
import com.example.study_mate.memberpreference.enums.Tendency;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.util.Set;

public record StudyCreateRequest(
        String title,
        String description,
        int maxMembers,
        StudyPurpose studyPurpose,
        Tendency tendency,
        String interest,
        Set<ActivityTime> activityTimes,
        Set<ActivityDay> activityDays
) {}