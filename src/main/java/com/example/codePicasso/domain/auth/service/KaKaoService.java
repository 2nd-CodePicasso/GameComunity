package com.example.codePicasso.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class KaKaoService {

    private static final String CLIENT_ID = "4b10ae0ea74ca704d208eb841074fa20";
    private static final String REDIRECT_URI = "http://obfgamers.com:8080/auth/hi/kakao/signup";
    private static final String KAKAO_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    private final WebClient webClient;

    public String requestKakaoToken(String code) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", CLIENT_ID);
        params.add("redirect_uri", REDIRECT_URI);
        params.add("code", code);

        try {
            // ✅ 동기 방식으로 토큰 요청
            Map<String, Object> response = webClient.post()
                    .uri(KAKAO_TOKEN_URL)
                    .header("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                    .bodyValue(params)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {}) // JSON 응답을 Map으로 변환
                    .block(); // ✅ 동기 방식으로 결과 반환 (비동기 X)

            // ✅ access_token 추출 후 반환
            return response != null ? (String) response.get("access_token") : null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    }

