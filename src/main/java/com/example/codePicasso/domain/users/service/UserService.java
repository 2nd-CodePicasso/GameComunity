package com.example.codePicasso.domain.users.service;

import com.example.codePicasso.global.exception.base.NotFoundException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    public void exception(){
        throw new NotFoundException(ErrorCode.USER_NOT_FOUND)
    }
}
