package com.example.codePicasso.domain.chat.dto.request;

import com.example.codePicasso.domain.chat.entity.Chat;
import com.example.codePicasso.domain.chat.entity.ChatRoom;
import com.example.codePicasso.domain.chat.entity.Notification;
import com.example.codePicasso.domain.user.entity.User;

public record NotificationRequest(
        Long messageId
        ) {

        public Notification toEntity(User user, Chat chat, ChatRoom chatRoom) {
                return Notification.builder()
                        .chatRoom(chatRoom)
                        .chat(chat)
                        .user(user)
                        .build();
        }
}
