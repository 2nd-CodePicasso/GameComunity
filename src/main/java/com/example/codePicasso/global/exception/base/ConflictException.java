package com.example.codePicasso.global.exception.base;

import com.example.codePicasso.global.exception.enums.ErrorCode;

public class ConflictException extends BusinessException {

    public ConflictException(ErrorCode errorCode) {
        super(errorCode);
    }
}
