package com.example.codePicasso.domain.posts.dto.response;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public record GetGameIdAllPostsResponse(
        Long postId,
        String nickname,
        Long gameId,
        String categoryName,
        String title,
        LocalDateTime createdAt
) {
}
