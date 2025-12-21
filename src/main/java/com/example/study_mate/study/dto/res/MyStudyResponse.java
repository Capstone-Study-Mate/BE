package com.example.study_mate.study.dto.res;

import com.example.study_mate.study.domain.Study;

public record MyStudyResponse(
        Long studyId,
        String title,
        int currentMembers,
        int maxMembers,
        String leaderName
) {

    public static MyStudyResponse from(Study study) {
        return new MyStudyResponse(
                study.getId(),
                study.getTitle(),
                study.getCurrentMembers(),
                study.getMaxMembers(),
                study.getLeader().getNickname()
        );
    }
}
