package com.example.codePicasso.domain.post.dto.response;

import com.example.codePicasso.domain.post.enums.PostStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PostResponse(
        Long postId,
        Long gameId,
        String gameTitle,
        Long categoryId,
        String categoryName,
        String title,
        String nickname,
        String description,
        Integer viewCount,
        PostStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    @QueryProjection
    public PostResponse(
            Long postId,
            Long gameId,
            String gameTitle,
            Long categoryId,
            String categoryName,
            String title,
            String nickname,
            String description,
            Integer viewCount,
            PostStatus status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.postId = postId;
        this.gameId = gameId;
        this.gameTitle = gameTitle;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.title = title;
        this.nickname = nickname;
        this.description = description;
        this.viewCount = viewCount;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
