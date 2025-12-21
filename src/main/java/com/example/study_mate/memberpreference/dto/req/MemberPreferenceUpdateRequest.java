package com.example.study_mate.memberpreference.dto.req;

import com.example.study_mate.memberpreference.enums.ActivityDay;
import com.example.study_mate.memberpreference.enums.ActivityTime;
import com.example.study_mate.memberpreference.enums.StudyPurpose;
import com.example.study_mate.memberpreference.enums.Tendency;

import java.util.Set;

public record MemberPreferenceUpdateRequest(
        StudyPurpose studyPurpose,
        Tendency tendency,
        String interest,
        Set<ActivityTime> activityTimes,
        Set<ActivityDay> activityDays
) {}
