package com.example.study_mate.global.exception;

import com.example.study_mate.global.exception.code.ErrorCode;
import com.example.study_mate.global.exception.code.GeneralErrorCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage(), null, false, false);
        this.errorCode = errorCode;
    }

    public static final BusinessException INVALID_STATE = new BusinessException(GeneralErrorCode.INVALID_STATE);
}
