package com.example.codePicasso.domain.chat.dto.request;

public record UpdateRoomRequest(
        Long roomId,
        String name,
        String username
) {
}
