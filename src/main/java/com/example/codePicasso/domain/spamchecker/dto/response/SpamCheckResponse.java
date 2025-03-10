package com.example.codePicasso.domain.spamchecker.dto.response;

import lombok.Getter;

@Getter
public class SpamCheckResponse {
    private String number;
    private String spam;
    private String spam_count;
    private String registed_date;
    private String cyber_crime;
    private int success;
}

