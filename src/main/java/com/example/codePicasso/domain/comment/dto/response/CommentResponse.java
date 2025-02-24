package com.example.codePicasso.domain.comment.dto.response;

import com.example.codePicasso.domain.comment.entity.Comment;
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
    public static CommentResponse toDto(Comment comment) {
        return CommentResponse.builder().
                commentId(comment.getId()).
                postId(comment.getPost().getId()).
                userId(comment.getUser().getId()).
                nickname(comment.getUser().getNickname()).
                text(comment.getText()).
                replyListResponse(ReplyListResponse.builder()
                        .responses(comment.getReplies().stream()
                                .map(ReplyResponse::toDto)
                                .toList())
                        .build()).
                createdAt(comment.getCreatedAt()).
                updatedAp(comment.getUpdatedAt()).
                build();
    }
}
