package com.example.codePicasso.domain.chat.dto.response;

import lombok.Builder;

@Builder
public record ImageResponse(
        String imageUrl
) {
}
