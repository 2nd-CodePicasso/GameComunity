package com.example.codePicasso.domain.chat.service;

import com.example.codePicasso.domain.chat.dto.response.GlobalChatResponse;
import com.example.codePicasso.domain.chat.entity.Chat;


public class ToDto {
    
    public static GlobalChatResponse from(Chat chat) {
        return GlobalChatResponse.builder()
                .chatsId(chat.getId())
                .message(chat.getContent())
                .sender(chat.getUsername())
                .createdAt(chat.getCreatedAt())
                .build();
    }
}
