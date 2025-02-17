package com.example.codePicasso.domain.chat.service;

import com.example.codePicasso.domain.chat.dto.response.ChatResponse;
import com.example.codePicasso.domain.chat.entity.Chat;


public class ToDto {
    
    public static ChatResponse from(Chat chat) {
        return ChatResponse.builder()
                .chatsId(chat.getId())
                .message(chat.getContent())
                .sender(chat.getUsername())
                .createdAt(chat.getCreatedAt())
                .build();
    }
}
