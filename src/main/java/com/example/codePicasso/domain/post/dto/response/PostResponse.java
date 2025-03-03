package com.example.codePicasso.domain.post.dto.response;

import com.example.codePicasso.domain.post.enums.PostStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PostResponse(
        Long postId,
        Long gameId,
        String categoryName,
        String title,
        String nickname,
        String description,
        Integer viewCount,
        PostStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
