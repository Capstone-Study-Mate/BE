package com.example.study_mate.member.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public record MemberLoginReqDTO(
        @NotBlank String username,
        @NotBlank String password
)
{}
