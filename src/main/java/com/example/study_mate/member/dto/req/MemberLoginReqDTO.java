package com.example.study_mate.member.dto.req;

import com.example.study_mate.member.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public record MemberLoginReqDTO(
        @NotBlank
        String username,
        @NotBlank
        String password

)
{}
