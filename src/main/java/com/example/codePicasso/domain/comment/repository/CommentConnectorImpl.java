package com.example.codePicasso.domain.comment.repository;

import com.example.codePicasso.domain.comment.entity.Comment;
import com.example.codePicasso.domain.comment.service.CommentConnector;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CommentConnectorImpl implements CommentConnector {
    private final CommentRepository commentRepository;

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> findCommentByPostId(Long postId) {
        return commentRepository.findCommentByPostId(postId);
    }

    @Override
    public Optional<Comment> findByCommentIdAndUserId(Long commentId, Long userId) {
        return commentRepository.findByCommentIdAndUserId(commentId, userId);
    }

    @Override
    public void delete(Comment deleteComment) {
        commentRepository.delete(deleteComment);
    }
}
