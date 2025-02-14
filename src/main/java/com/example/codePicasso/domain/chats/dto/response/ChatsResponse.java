package com.example.codePicasso.domain.chats.dto.response;

import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
public record ChatsResponse(Long chatsId, String sender, String message, LocalDateTime createdAt) {

}
