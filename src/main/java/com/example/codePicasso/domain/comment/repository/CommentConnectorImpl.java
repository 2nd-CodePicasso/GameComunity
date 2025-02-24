package com.example.codePicasso.domain.comment.repository;

import com.example.codePicasso.domain.comment.entity.Comment;
import com.example.codePicasso.domain.comment.service.CommentConnector;
import com.example.codePicasso.global.exception.base.InvalidRequestException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentConnectorImpl implements CommentConnector {
    private final CommentRepository commentRepository;

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> findAllByPostId(Long postId) {
        return commentRepository.findAllByPostId(postId);
    }

    @Override
    public Comment findByIdAndUserId(Long commentId, Long userId) {
        return commentRepository.findByIdAndUserId(commentId, userId)
                .orElseThrow(() -> new InvalidRequestException(ErrorCode.COMMENT_NOT_FOUND));
    }

    @Override
    public void delete(Comment deleteComment) {
        commentRepository.delete(deleteComment);
    }

    @Override
    public Comment findById(Long parentId) {
        return commentRepository.findById(parentId)
                .orElseThrow(() -> new InvalidRequestException(ErrorCode.COMMENT_NOT_FOUND));
    }

}
