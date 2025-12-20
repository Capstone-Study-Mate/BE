package com.example.study_mate.memberpreference.dto;

import com.example.study_mate.memberpreference.enums.ActivityDay;
import com.example.study_mate.memberpreference.enums.ActivityTime;

import java.util.Set;

public record MemberPreferenceUpdateRequest(
        String studyPurpose,
        String interest,
        String tendency,
        Set<ActivityTime> activityTimes,
        Set<ActivityDay> activityDays
) {}
