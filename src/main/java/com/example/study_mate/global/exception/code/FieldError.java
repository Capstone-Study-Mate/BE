package com.example.study_mate.global.exception.code;

public record FieldError(
        String field,
        String reason
) {

    public static FieldError of(org.springframework.validation.FieldError fieldError) {
        return new FieldError(fieldError.getField(), fieldError.getDefaultMessage());
    }
}
