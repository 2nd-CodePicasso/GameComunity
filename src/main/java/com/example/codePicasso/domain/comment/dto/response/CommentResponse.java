package com.example.codePicasso.domain.comment.dto.response;

import com.example.codePicasso.domain.comment.entity.Comment;
import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record CommentResponse(
        Long commentId,
        Long postId,
        String nickname,
        String text,
        LocalDateTime createdAt,
        LocalDateTime updatedAp
) {
    public static CommentResponse toDto(Comment comment) {
        return CommentResponse.builder().
                commentId(comment.getId()).
                postId(comment.getPost().getId()).
                nickname(comment.getUser().getNickname()).
                text(comment.getText()).
                createdAt(comment.getCreatedAt()).
                updatedAp(comment.getUpdatedAt()).
                build();
    }
}
