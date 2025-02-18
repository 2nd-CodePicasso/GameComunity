package com.example.codePicasso.domain.post.dto.response;

import com.example.codePicasso.domain.post.entity.Post;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PostResponse(
        Long postId,
        Long gameId,
        Long categoryId,
        String categoryName,
        String nickname,
        String title,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    // Post Entity > PostResponse 메서드 경로 이동
    public static PostResponse toDto(Post post) {
        return PostResponse.builder().
                postId(post.getId()).
                gameId(post.getGame().getId()).
                categoryId(post.getCategory().getId()).
                categoryName(post.getCategory().getCategoryName()).
                nickname(post.getUser().getNickname()).
                title(post.getTitle()).
                description(post.getDescription()).
                createdAt(post.getCreatedAt()).
                updatedAt(post.getUpdatedAt()).
                build();
    }
}
