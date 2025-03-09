package com.example.codePicasso.domain.user.service;

import com.example.codePicasso.domain.user.dto.request.UserRequest;
import com.example.codePicasso.domain.user.dto.response.UserResponse;
import com.example.codePicasso.domain.user.entity.User;
import com.example.codePicasso.global.common.DtoFactory;
import com.example.codePicasso.global.config.PasswordEncoder;
import com.example.codePicasso.global.exception.base.BusinessException;
import com.example.codePicasso.global.exception.base.DuplicateException;
import com.example.codePicasso.global.exception.base.InvalidRequestException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

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

        Long kakaoId = getKakaoId(userRequest.kakaoToken());

        String encodePassword = passwordEncoder.encode(userRequest.password());

        User user = userRequest.toEntity(encodePassword,kakaoId);
        userConnector.save(user);
        return DtoFactory.toUserDto(user);
    }

    private Long getKakaoId(String kakaoToken) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Authorization", "Bearer " + kakaoToken);
            httpHeaders.set("Content-Type","application/x-www-form-urlencoded;charset=utf-8");

            HttpEntity<?> entity = new HttpEntity<>(httpHeaders);
            ResponseEntity<Map> response = restTemplate.exchange(
                    "https://kapi.kakao.com/v2/user/me",
                    HttpMethod.GET,
                    entity,
                    Map.class
            );

            Map<String, Object> body = response.getBody();
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
