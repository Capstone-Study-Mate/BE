package com.example.study_mate.member.dto.res;

import lombok.Builder;
import lombok.Getter;


@Builder
public record MemberLoginResDTO(
        Long memberId,
        String accessToken
){}