package com.example.codePicasso.domain.chats.dto.request;

import com.example.codePicasso.domain.chats.entity.Chats;

public record ChatsRequest(String sender ,String message) {

    public Chats toEntity(Long userId) {
        return Chats.builder()
                .userId(userId)
                .username(sender)
                .content(message)
                .build();
    }
}
