package com.example.codePicasso.domain.chat.dto.response;

import com.example.codePicasso.domain.chat.entity.GlobalChat;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
public record GlobalChatDto(
        Long userId,
        String username,
        String message,
        String imageUrl,
        LocalDateTime createdAt
) implements Serializable
{
    public GlobalChat toEntity() {
        return GlobalChat.builder()
                .username(username)
                .userId(userId)
                .content(message)
                .imageUrl(imageUrl)
                .createdAt(createdAt)
                .build();
    }
}
