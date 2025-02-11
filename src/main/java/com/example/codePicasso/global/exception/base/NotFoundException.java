package com.example.codePicasso.global.exception.base;

import com.example.codePicasso.global.exception.enums.ErrorCode;

public class NotFoundException extends BusinessException {

  public NotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }
}
