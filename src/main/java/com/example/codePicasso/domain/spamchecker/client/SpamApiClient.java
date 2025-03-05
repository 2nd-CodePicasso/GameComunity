package com.example.codePicasso.domain.spamchecker.client;

import com.example.codePicasso.domain.spamchecker.config.ApiConfig;
import com.example.codePicasso.domain.spamchecker.dto.request.SpamCheckRequest;
import com.example.codePicasso.domain.spamchecker.dto.response.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@AllArgsConstructor
public class SpamApiClient {

    private final RestTemplate restTemplate;
    private final ApiConfig apiConfig;

    public ApiResponse checkSpamNumber(String number) {
        String url = apiConfig.getApiUrl();

        HttpHeaders headers = new HttpHeaders();
        headers.set("CL_AUTH_KEY", apiConfig.getAuthKey());
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<SpamCheckRequest> requestEntity = new HttpEntity<>(new SpamCheckRequest(number), headers);

        ResponseEntity<ApiResponse> responseEntity = restTemplate.exchange(
                url, HttpMethod.POST, requestEntity, ApiResponse.class);

        return responseEntity.getBody();
    }
}

