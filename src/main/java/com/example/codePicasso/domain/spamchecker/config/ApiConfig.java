package com.example.codePicasso.domain.spamchecker.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig {

    @Value("${api.spam-check.url}")
    private String apiUrl;

    @Value("${api.spam-check.auth-key}")
    private String authKey;

    public String getApiUrl() {
        return apiUrl;
    }

    public String getAuthKey() {
        return authKey;
    }
}