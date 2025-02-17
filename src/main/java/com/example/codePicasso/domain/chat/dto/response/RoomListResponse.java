package com.example.codePicasso.domain.chat.dto.response;

import com.example.codePicasso.domain.chat.entity.ChatRoom;
import lombok.Builder;

import java.util.List;

@Builder
public record RoomListResponse(
        List<RoomResponse> roomResponses
) {
}
