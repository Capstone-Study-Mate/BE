package com.example.study_mate.member.dto.req;

import jakarta.validation.constraints.Email;

    public record MemberUpdateRequest(
            @Email
            String email,
            String nickname,
            String major,
            String phoneNumber
    ) {}

