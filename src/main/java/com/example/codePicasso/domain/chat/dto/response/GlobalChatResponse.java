package com.example.codePicasso.domain.chat.dto.response;

import com.example.codePicasso.domain.chat.entity.GlobalChat;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
public record GlobalChatResponse(
        Long chatsId,
        String username,
        String message,
        String imageUrl,
        LocalDateTime createdAt
)   {


}
