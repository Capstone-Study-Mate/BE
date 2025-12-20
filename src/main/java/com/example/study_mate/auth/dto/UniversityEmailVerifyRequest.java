package com.example.study_mate.auth.dto;

public record UniversityEmailVerifyRequest(
        String email,
        String code
) {}
