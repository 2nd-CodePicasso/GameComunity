package com.example.codePicasso.domain.chat.controller;

import com.example.codePicasso.domain.chat.dto.response.NotificationResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record NotificationListResponse(
        List<NotificationResponse> notificationResponses
) {
}
