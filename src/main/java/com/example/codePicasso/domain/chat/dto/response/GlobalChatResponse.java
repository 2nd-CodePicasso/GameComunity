package com.example.codePicasso.domain.chat.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record GlobalChatResponse(
        Long chatsId,
        String username,
        String message,
        LocalDateTime createdAt
) {

}
