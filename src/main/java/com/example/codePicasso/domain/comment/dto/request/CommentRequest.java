package com.example.codePicasso.domain.comment.dto.request;

import com.example.codePicasso.domain.comment.entity.Comment;
import com.example.codePicasso.domain.post.entity.Post;
import com.example.codePicasso.domain.user.entity.User;
import jakarta.validation.constraints.NotBlank;

public record CommentRequest(
        @NotBlank
        String text
) {
    // 댓글 생성 메서드
    public static Comment toEntityForComment(Post post, User user, String text) {
        return Comment.builder()
                .post(post)
                .user(user)
                .text(text)
                .build();
    }

    // 대댓글 생성 메서드
    public static Comment toEntityForReply(User user, Comment parent, String text) {
        return Comment.builder()
                .post(parent.getPost())
                .user(user)
                .parent(parent)
                .text(text)
                .build();
    }
}
