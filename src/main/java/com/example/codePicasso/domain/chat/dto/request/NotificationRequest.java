package com.example.codePicasso.domain.chat.dto.request;

import com.example.codePicasso.domain.chat.entity.Chat;
import com.example.codePicasso.domain.chat.entity.ChatRoom;
import com.example.codePicasso.domain.chat.entity.Notification;
import com.example.codePicasso.domain.user.entity.User;

import java.time.LocalDateTime;

public record NotificationRequest(
        Long messageId,
        String authorName,
        String content,
        LocalDateTime createdTime
        ) {

        public Notification toEntity(User user, Chat chat, ChatRoom chatRoom) {
                return Notification.builder()
                        .authorName(authorName)
                        .writtenTime(createdTime)
                        .content(content)
                        .chatRoom(chatRoom)
                        .chat(chat)
                        .user(user)
                        .build();
        }
}
