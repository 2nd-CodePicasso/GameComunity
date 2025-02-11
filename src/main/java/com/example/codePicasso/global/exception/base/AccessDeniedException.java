package com.example.codePicasso.global.exception.base;


import com.example.codePicasso.global.exception.enums.ErrorCode;

public class AccessDeniedException extends BusinessException {

  public AccessDeniedException(ErrorCode errorCode) {
    super(errorCode);
  }
}
