package com.example.codePicasso.domain.user.service;

import com.example.codePicasso.domain.auth.service.KaKaoService;
import com.example.codePicasso.domain.user.dto.request.UserRequest;
import com.example.codePicasso.domain.user.dto.response.UserInfoResponse;
import com.example.codePicasso.domain.user.dto.response.UserResponse;
import com.example.codePicasso.domain.user.entity.User;
import com.example.codePicasso.global.common.DtoFactory;
import com.example.codePicasso.global.config.PasswordEncoder;
import com.example.codePicasso.global.exception.base.BusinessException;
import com.example.codePicasso.global.exception.base.DuplicateException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserConnector userConnector;
    private final PasswordEncoder passwordEncoder;
    private final WebClient webClient;
    private final KaKaoService kaKaoService;

    @Transactional
    public UserResponse addUser(UserRequest userRequest) {
        if (userConnector.existsByLoginId(userRequest.loginId())) {
            throw new DuplicateException(ErrorCode.ID_ALREADY_EXISTS);
        }

        Long kakaoId = kaKaoService.getKakaoId(userRequest.kakaoToken());

        String encodePassword = passwordEncoder.encode(userRequest.password());

        User user = userRequest.toEntity(encodePassword, kakaoId);
        userConnector.save(user);

        return DtoFactory.toUserDto(user);
    }

    public UserInfoResponse getUserInfo(Long userId) {
        User user = userConnector.findById(userId);

        return DtoFactory.toUserInfoDto(user);
    }
}
