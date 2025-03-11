package com.example.codePicasso.global.exception.base;

import com.example.codePicasso.global.exception.enums.ErrorCode;

public class ImageIoException extends BusinessException {
    public ImageIoException(ErrorCode errorCode) {
        super(errorCode);
    }
}
