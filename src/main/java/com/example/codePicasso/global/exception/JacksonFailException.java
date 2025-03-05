package com.example.codePicasso.global.exception;

import com.example.codePicasso.global.exception.base.BusinessException;
import com.example.codePicasso.global.exception.enums.ErrorCode;

public class JacksonFailException extends BusinessException {
    public JacksonFailException(ErrorCode errorCode) {
        super(errorCode);
    }
}
