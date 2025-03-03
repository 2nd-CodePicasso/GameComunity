package com.example.codePicasso.domain.chat.dto.request;

import com.example.codePicasso.domain.chat.entity.ChatRoom;
import com.example.codePicasso.domain.user.entity.User;

public record RoomRequest(
        String name,
        boolean isSecurity,
        String password
) {

    public ChatRoom toEntity(User user, String encodedPassword) {
        return ChatRoom.builder()
                .name(name)
                .user(user)
                .password(encodedPassword)
                .isSecurity(isSecurity)
                .build();
    }
}
