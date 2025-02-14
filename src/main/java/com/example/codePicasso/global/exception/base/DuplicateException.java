package com.example.codePicasso.global.exception.base;

import com.example.codePicasso.global.exception.enums.ErrorCode;

public class DuplicateException extends BusinessException{
    public DuplicateException(ErrorCode errorCode) {
        super(errorCode);
    }
}
