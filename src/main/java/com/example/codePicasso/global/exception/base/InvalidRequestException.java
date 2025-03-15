package com.example.codePicasso.global.exception.base;

import com.example.codePicasso.global.exception.enums.ErrorCode;

public class InvalidRequestException extends BusinessException {

    public InvalidRequestException(ErrorCode errorCode) {
        super(errorCode);
    }
}
