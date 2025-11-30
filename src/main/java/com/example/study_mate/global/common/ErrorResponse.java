package com.example.study_mate.global.common;

import com.example.study_mate.global.exception.code.ErrorCode;
import com.example.study_mate.global.exception.code.FieldError;
import lombok.Builder;

import java.time.Instant;
import java.util.List;

@Builder
public record ErrorResponse(

        int status,
        String message,
        String path,
        Instant timestamp,
        List<FieldError> errors
) {

    public static ErrorResponse of(ErrorCode code, String path) {
        return ErrorResponse.builder()
                .status(code.getStatus())
                .message(code.getMessage())
                .path(path)
                .timestamp(Instant.now())
                .build();
    }

    public static ErrorResponse of(ErrorCode code, String path, List<FieldError> fieldErrors) {
        return ErrorResponse.builder()
                .status(code.getStatus())
                .message(code.getMessage())
                .path(path)
                .timestamp(Instant.now())
                .errors(fieldErrors)
                .build();
    }
}
