package com.example.codePicasso.domain.comment.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record CommentResponse(
        Long commentId,
        Long postId,
        Long userId,
        Long parentId,
        String nickname,
        String text,
        LocalDateTime createdAt,
        LocalDateTime updatedAp,
        List<CommentResponse> replies
) {
}