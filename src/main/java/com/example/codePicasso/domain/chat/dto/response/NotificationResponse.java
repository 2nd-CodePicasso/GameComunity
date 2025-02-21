package com.example.codePicasso.domain.chat.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record NotificationResponse(
        Long notificationId,
        Long messageId,
        Long chatRoomId,
        String authorName,
        String content,
        LocalDateTime writtenTime,
        LocalDateTime createdTime,
        String proposerName
) {
}
