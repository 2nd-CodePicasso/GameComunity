package com.example.codePicasso.domain.comment.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CommentResponse(
        Long commentId,
        Long postId,
        Long userId,
        String nickname,
        String text,
        ReplyListResponse replyListResponse,
        LocalDateTime createdAt,
        LocalDateTime updatedAp
) {
}
