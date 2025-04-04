package com.example.codePicasso.domain.auth.service;

import com.example.codePicasso.global.exception.base.BusinessException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class KaKaoService {

    @Value("${kakao.clientId}")
    private String clientId;
    @Value("${kakao.redirectURI}")
    private String redirectUri;
    @Value("${kakao.tokenURL}")
    private String kakaoTokenUrl;
    private final WebClient webClient;

    public String requestKakaoToken(String code) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);

        try {
            Map<String, Object> response = webClient.post()
                    .uri(kakaoTokenUrl)
                    .header("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                    .bodyValue(params)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                    .block();

            return response != null ? (String) response.get("access_token") : null;

        } catch (Exception e) {
            log.info(e.getMessage());
            throw new BusinessException(ErrorCode.KAKAO_EXCEPTION);
        }
    }
    public Long getKakaoId(String kakaoToken) {
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

