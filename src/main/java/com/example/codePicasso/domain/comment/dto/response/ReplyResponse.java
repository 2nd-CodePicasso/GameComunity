package com.example.codePicasso.domain.comment.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ReplyResponse(
        Long commentId,
        Long postId,
        Long userId,
        Long parentId,
        String nickname,
        String text,
        LocalDateTime createdAt,
        LocalDateTime updatedAp
) {
}
