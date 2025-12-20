package com.example.study_mate.study.dto.req;

import com.example.study_mate.memberpreference.enums.ActivityDay;
import com.example.study_mate.memberpreference.enums.ActivityTime;

import java.util.Set;

public record StudyCreateRequest(
        String title,
        String description,
        int maxMembers,
        Set<ActivityTime> activityTimes,
        Set<ActivityDay> activityDays
) {}