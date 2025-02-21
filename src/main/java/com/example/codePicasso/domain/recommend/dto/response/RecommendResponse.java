package com.example.codePicasso.domain.recommend.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record RecommendResponse(
        Long id,
        Long userId,
        Long postId,
        LocalDateTime createdAt
) {
}
