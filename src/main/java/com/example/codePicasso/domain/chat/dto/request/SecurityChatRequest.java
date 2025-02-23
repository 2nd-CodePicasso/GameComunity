package com.example.codePicasso.domain.chat.dto.request;

public record SecurityChatRequest(
        Long roomId,
        String password
) {
}
