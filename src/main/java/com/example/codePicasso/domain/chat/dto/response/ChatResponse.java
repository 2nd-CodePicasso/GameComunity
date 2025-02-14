package com.example.codePicasso.domain.chat.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ChatResponse(Long chatsId, String sender, String message, LocalDateTime createdAt) {

}
