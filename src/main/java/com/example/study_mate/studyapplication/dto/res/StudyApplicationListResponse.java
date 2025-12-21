package com.example.study_mate.studyapplication.dto.res;

import com.example.study_mate.member.domain.Member;
import com.example.study_mate.member.enums.Gender;
import com.example.study_mate.studyapplication.domain.StudyApplication;

public record StudyApplicationListResponse (
        Long applicationId,
        Long memberId,
        Gender gender,
        String nickname,
        String phoneNumber
){
    public static StudyApplicationListResponse from(StudyApplication application) {
    Member member = application.getMember();

    return new StudyApplicationListResponse(
            application.getId(),
            member.getId(),
            member.getGender(),
            member.getNickname(),
            member.getPhoneNumber()
    );
    }
}
