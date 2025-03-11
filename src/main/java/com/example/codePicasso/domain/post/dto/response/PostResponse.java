package com.example.codePicasso.domain.post.dto.response;

import com.example.codePicasso.domain.post.enums.PostStatus;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@class"
)
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
