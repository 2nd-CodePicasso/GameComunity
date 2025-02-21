package com.example.codePicasso.domain.post.dto.response;

import com.example.codePicasso.domain.post.entity.Post;
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
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    // Post Entity > PostResponse 메서드 경로 이동

}
