package com.example.codePicasso.domain.chat.dto.request;

import com.example.codePicasso.domain.chat.entity.ChatRoom;
import com.example.codePicasso.domain.user.entity.User;

public record RoomRequest(
        String name) {

    public ChatRoom toEntity(User user) {
        return ChatRoom.builder()
                .name(name)
                .user(user)
                .build();
    }
}
