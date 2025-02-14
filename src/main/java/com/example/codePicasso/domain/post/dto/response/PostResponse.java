package com.example.codePicasso.domain.post.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PostResponse(
        Long postId,
        Long gameId,
        String categoryName,
        String title,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
