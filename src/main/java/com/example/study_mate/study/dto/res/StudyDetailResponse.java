package com.example.study_mate.study.dto.res;

import com.example.study_mate.study.domain.Study;
import com.example.study_mate.memberpreference.enums.ActivityDay;
import com.example.study_mate.memberpreference.enums.ActivityTime;

import java.util.Set;

public record StudyDetailResponse(
        Long studyId,
        String title,
        String description,
        String universityName,
        String leaderName,
        int currentMembers,
        int maxMembers,
        Set<ActivityTime> activityTimes,
        Set<ActivityDay> activityDays
) {
    public static StudyDetailResponse from(Study study) {
        return new StudyDetailResponse(
                study.getId(),
                study.getTitle(),
                study.getDescription(),
                study.getUniversity().getName(),
                study.getLeader().getNickname(),
                study.getCurrentMembers(),
                study.getMaxMembers(),
                study.getActivityTimes(),
                study.getActivityDays()
        );
    }
}
