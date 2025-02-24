package com.example.codePicasso.global.exception.base;

import com.example.codePicasso.global.exception.enums.ErrorCode;

public class DataAccessException extends BusinessException {
    
    public DataAccessException(ErrorCode errorCode) {
        super(errorCode);
    }
}
