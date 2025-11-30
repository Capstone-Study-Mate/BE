package com.example.study_mate.member.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public record MemberLoginReqDto(
        @NotBlank String username,
        @NotBlank String password
)
{}
