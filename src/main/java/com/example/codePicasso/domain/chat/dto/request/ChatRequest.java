package com.example.codePicasso.domain.chat.dto.request;

import com.example.codePicasso.domain.chat.entity.Chat;

public record ChatRequest(String sender , String message) {

    public Chat toEntity(Long userId) {
        return Chat.builder()
                .userId(userId)
                .username(sender)
                .content(message)
                .build();
    }
}
