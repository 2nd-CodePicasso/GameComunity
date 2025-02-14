package com.example.codePicasso.domain.post.dto.response;

import java.time.LocalDateTime;

public record GetGameIdAllPostsResponse(
        Long postId,
        String nickname,
        Long gameId,
        String categoryName,
        String title,
        LocalDateTime createdAt
) {
}
