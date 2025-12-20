package com.example.study_mate.member.dto.res;

import com.example.study_mate.member.domain.Member;

public record MemberResponse(
        Long id,
        String email,
        String name,
        String nickname,
        String major,
        String phoneNumber
) {
    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getEmail(),
                member.getName(),
                member.getNickname(),
                member.getMajor(),
                member.getPhoneNumber()
        );
    }
}
