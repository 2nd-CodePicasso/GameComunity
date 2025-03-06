package com.example.codePicasso.global.exception.base;

import com.example.codePicasso.global.exception.enums.ErrorCode;

public class RabbitException extends BusinessException {
    public RabbitException(ErrorCode errorCode) {
        super(errorCode);
    }
}
