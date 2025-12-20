package com.example.study_mate.member.dto.res;

import jakarta.validation.constraints.Email;

public record JwtTokenPair(
        String accessToken,
        String refreshToken
) {
}