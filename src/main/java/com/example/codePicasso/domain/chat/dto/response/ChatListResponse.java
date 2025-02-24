package com.example.codePicasso.domain.chat.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ChatListResponse(
        List<ChatResponse> chatResponses
) {
}
