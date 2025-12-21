package com.example.study_mate.study.dto.res;

import com.example.study_mate.study.domain.Study;
import com.example.study_mate.memberpreference.enums.ActivityDay;
import com.example.study_mate.memberpreference.enums.ActivityTime;

import java.util.Set;

public record StudyListResponse(
        Long studyId,
        String title,
        String leaderName,
        int currentMembers,
        int maxMembers,
        Set<ActivityTime> activityTimes,
        Set<ActivityDay> activityDays
) {
    public static StudyListResponse from(Study study) {
        return new StudyListResponse(
                study.getId(),
                study.getTitle(),
                study.getLeader().getNickname(),
                study.getCurrentMembers(),
                study.getMaxMembers(),
                study.getActivityTimes(),
                study.getActivityDays()
        );
    }
}
