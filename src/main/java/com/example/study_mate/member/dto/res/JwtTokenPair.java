package com.example.study_mate.member.dto.res;

public record JwtTokenPair(
        String accessToken,
        String refreshToken
) {
}
