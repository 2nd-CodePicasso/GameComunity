package com.example.codePicasso.domain.user.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserInfoResponse(
        String nickname,
        LocalDateTime createdAt
) {
}
