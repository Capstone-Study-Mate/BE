package com.example.study_mate.global.exception;


import com.example.study_mate.global.common.ErrorResponse;
import com.example.study_mate.global.exception.code.ErrorCode;
import com.example.study_mate.global.exception.code.FieldError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.List;

@Component
public class ErrorResponseFactory {

    public ErrorResponse from(HttpServletRequest request, ErrorCode code) {
        return ErrorResponse.of(code, request.getRequestURI());
    }

    public ErrorResponse fromValidation(HttpServletRequest request, BindingResult bindingResult, ErrorCode code) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors()
                .stream()
                .map(FieldError::of)
                .toList();

        return ErrorResponse.of(code, request.getRequestURI(), fieldErrors);
    }
}
