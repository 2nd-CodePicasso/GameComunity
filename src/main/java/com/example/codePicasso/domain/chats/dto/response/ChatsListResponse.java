package com.example.codePicasso.domain.chats.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ChatsListResponse(List<ChatsResponse> chatsResponseList) {
}
