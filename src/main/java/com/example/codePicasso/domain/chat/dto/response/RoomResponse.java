package com.example.codePicasso.domain.chat.dto.response;

import lombok.Builder;

@Builder
public record RoomResponse(
        Long roomId,
        String username,
        String roomName,
        boolean isSecurity
        ) {
}
