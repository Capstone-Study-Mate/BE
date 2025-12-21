package com.example.study_mate.memberpreference.dto.res;

import com.example.study_mate.memberpreference.enums.ActivityDay;
import com.example.study_mate.memberpreference.enums.ActivityTime;
import com.example.study_mate.memberpreference.enums.StudyPurpose;
import com.example.study_mate.memberpreference.enums.Tendency;

import java.util.Set;

public record MemberPreferenceResponse(
        StudyPurpose studyPurpose,
        String interest,
        Tendency tendency,
        Set<ActivityTime> activityTimes,
        Set<ActivityDay> activityDays
) {}
