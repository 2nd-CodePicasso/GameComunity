package com.example.codePicasso.domain.user.service;

import com.example.codePicasso.domain.user.dto.request.UserRequest;
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

    // Todo ???
    public void exception() {
    }

    @Transactional
    public UserResponse addUser(UserRequest userRequest) {
        if (userConnector.existsByLoginId(userRequest.loginId())) {
            throw new DuplicateException(ErrorCode.ID_ALREADY_EXISTS);
        }

        Long kakaoId = getKakaoId(userRequest.kakaoToken());

        String encodePassword = passwordEncoder.encode(userRequest.password());

        User user = userRequest.toEntity(encodePassword, kakaoId);
        userConnector.save(user);

        return DtoFactory.toUserDto(user);
    }

    private Long getKakaoId(String kakaoToken) {
        try {
            Map<String, Object> body = webClient.get()
                    .uri("https://kapi.kakao.com/v2/user/me")
                    .header("Authorization", "Bearer " + kakaoToken)
                    .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                    })
                    .block();

            if (body != null) {
                String string = body.get("id").toString();

                return Long.valueOf(string);
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new BusinessException(ErrorCode.KAKAO_EXCEPTION);
        }
        return null;
    }
}
