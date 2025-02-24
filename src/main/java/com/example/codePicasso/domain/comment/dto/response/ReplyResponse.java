package com.example.codePicasso.domain.comment.dto.response;

import com.example.codePicasso.domain.comment.entity.Comment;
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
    public static ReplyResponse toDto(Comment comment) {
        return ReplyResponse.builder().
                commentId(comment.getId()).
                postId(comment.getPost().getId()).
                userId(comment.getUser().getId()).
                parentId(comment.getParent().getId()).
                nickname(comment.getUser().getNickname()).
                text(comment.getText()).
                createdAt(comment.getCreatedAt()).
                updatedAp(comment.getUpdatedAt()).
                build();
    }
}
