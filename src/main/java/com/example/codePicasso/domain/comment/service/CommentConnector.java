package com.example.codePicasso.domain.comment.service;

import com.example.codePicasso.domain.comment.entity.Comment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CommentConnector {

    Comment save(Comment comment);

    List<Comment> findAllByPostId(Long postId);

    Comment findByIdAndUserId(Long commentId, Long userId);

    void delete(Comment deleteComment);

    Comment findById(Long parentId);
}
