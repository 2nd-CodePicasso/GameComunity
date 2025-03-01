package com.example.codePicasso.domain.comment.repository;

import com.example.codePicasso.domain.comment.entity.Comment;
import com.example.codePicasso.domain.comment.entity.QComment;
import com.example.codePicasso.domain.comment.service.CommentConnector;
import com.example.codePicasso.global.exception.base.InvalidRequestException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.codePicasso.domain.comment.entity.QComment.comment;
import static com.example.codePicasso.domain.post.entity.QPost.post;
import static com.example.codePicasso.domain.user.entity.QUser.user;

@Component
@RequiredArgsConstructor
public class CommentConnectorImpl implements CommentConnector {
    private final CommentRepository commentRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> findAllByPostId(Long postId) {
        QComment replies = new QComment("replies");

        return queryFactory.select(comment)
                .from(comment)
                .leftJoin(comment.user, user).fetchJoin()
                .leftJoin(comment.post, post).fetchJoin()
                .leftJoin(comment.replies, replies).fetchJoin()
                .where(comment.post.id.eq(postId))
                .fetch();
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
