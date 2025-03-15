package com.example.codePicasso.domain.spamchecker.service;

import com.example.codePicasso.domain.spamchecker.client.SpamApiClient;
import com.example.codePicasso.domain.spamchecker.dto.response.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SpamCheckService {
    private final SpamApiClient spamApiClient;

    public ApiResponse checkSpam(String number) {
        return spamApiClient.checkSpamNumber(number);
    }
}
