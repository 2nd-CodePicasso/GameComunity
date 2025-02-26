package com.example.codePicasso.global.exception.base;

import com.example.codePicasso.global.exception.enums.ErrorCode;

import java.io.IOException;

public class ImageIoException extends BusinessException {
    public ImageIoException(ErrorCode errorCode) {
        super(errorCode);
    }
}
