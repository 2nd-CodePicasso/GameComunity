package com.example.codePicasso.domain.chat.dto.request;

import com.example.codePicasso.domain.chat.entity.Chat;
import com.example.codePicasso.domain.chat.entity.ChatRoom;
import com.example.codePicasso.domain.chat.entity.GlobalChat;

public record ChatRequest(
        String username ,
        String message
) {

    public GlobalChat toEntityFromGlobalChat(Long userId) {
        return GlobalChat.builder()
                .userId(userId)
                .username(username)
                .content(message)
                .build();
    }

    public Chat toEntityFromChat(Long userId, ChatRoom chatRoom) {
        return Chat.builder()
                .userId(userId)
                .username(username)
                .content(message)
                .chatRoom(chatRoom)
                .build();
    }
}
