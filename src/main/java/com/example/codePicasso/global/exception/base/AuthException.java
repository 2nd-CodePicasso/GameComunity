package com.example.codePicasso.global.exception.base;


import com.example.codePicasso.global.exception.enums.ErrorCode;

public class AuthException extends BusinessException {

  public AuthException(ErrorCode errorCode) {
    super(errorCode);
  }
}
