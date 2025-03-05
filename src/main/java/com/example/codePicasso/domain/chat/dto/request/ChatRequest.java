package com.example.codePicasso.domain.chat.dto.request;

import com.example.codePicasso.domain.chat.entity.Chat;
import com.example.codePicasso.domain.chat.entity.ChatRoom;
import com.example.codePicasso.domain.chat.entity.GlobalChat;

public record ChatRequest(
        String message,
        String imageUrl
) {

    public GlobalChat toEntityFromGlobalChat(Long userId, String emoji, String username) {
        return GlobalChat.builder()
                .userId(userId)
                .imageUrl(imageUrl)
                .username(username)
                .content(emoji)
                .build();
    }

    public Chat toEntityFromChat(Long userId, ChatRoom chatRoom, String emoji, String username) {
        return Chat.builder()
                .userId(userId)
                .username(username)
                .content(emoji)
                .chatRoom(chatRoom)
                .build();
    }

}
