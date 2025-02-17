package com.example.codePicasso.domain.user.service;

import com.example.codePicasso.domain.user.dto.request.UserRequest;
import com.example.codePicasso.domain.user.dto.response.UserResponse;
import com.example.codePicasso.domain.user.entity.User;
import com.example.codePicasso.global.config.PasswordEncoder;
import com.example.codePicasso.global.exception.base.DuplicateException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserConnector userConnector;
    private final PasswordEncoder passwordEncoder;

    public void exception(){
    }

    @Transactional
    public UserResponse addUser(UserRequest userRequest) {
        if (userConnector.existsByLoginId(userRequest.loginId())) {
            throw new DuplicateException(ErrorCode.ID_ALREADY_EXISTS);
        }

        String encodePassword = passwordEncoder.encode(userRequest.password());

        User user = userRequest.toEntity(encodePassword);
        userConnector.save(user);
        return user.toDto();
    }

    public UserResponse updateUser(UserRequest userRequest, long l) {
        return null;
    }

}
