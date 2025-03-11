package com.example.codePicasso.domain.spamchecker.client;

import com.example.codePicasso.domain.spamchecker.config.ApiConfig;
import com.example.codePicasso.domain.spamchecker.dto.response.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
@AllArgsConstructor
public class SpamApiClient {

    private final RestTemplate restTemplate;
    private final ApiConfig apiConfig;

    public ApiResponse checkSpamNumber(String number) {
        String url = apiConfig.getApiUrl();

        HttpHeaders headers = new HttpHeaders();
        headers.set("CL_AUTH_KEY", apiConfig.getAuthKey());
        headers.setContentType(MediaType.MULTIPART_FORM_DATA); // 타입 문제

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("number", number);

        RequestEntity<MultiValueMap<String, String>> requestEntity = new RequestEntity<>(formData, headers, HttpMethod.POST, URI.create(url));

        ResponseEntity<ApiResponse> responseEntity = restTemplate.exchange(
                requestEntity, ApiResponse.class);

        return responseEntity.getBody();
    }
}
