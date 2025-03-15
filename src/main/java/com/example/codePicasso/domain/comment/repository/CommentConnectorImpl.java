package com.example.codePicasso.domain.comment.repository;

import com.example.codePicasso.domain.comment.entity.Comment;
import com.example.codePicasso.domain.comment.entity.QComment;
import com.example.codePicasso.domain.comment.service.CommentConnector;
import com.example.codePicasso.global.exception.base.InvalidRequestException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
import com.querydsl.jpa.impl.JPAQuery;
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
    private final QComment replies = new QComment("replies");
    private final QComment parent = new QComment("parent");

    private JPAQuery<Comment> baseCommentQuery() {
        return queryFactory.select(comment)
                .from(comment)
                .leftJoin(comment.user, user).fetchJoin()
                .leftJoin(comment.post, post).fetchJoin()
                .leftJoin(comment.replies, replies).fetchJoin();
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> findAllByPostId(Long postId) {
        List<Comment> comments = baseCommentQuery()
                .where(comment.post.id.eq(postId))
                .fetch();

        if (comments.isEmpty()) {
            throw new InvalidRequestException(ErrorCode.COMMENT_NOT_FOUND);
        }

        return comments;
    }

    @Override
    public Comment findByIdAndUserId(Long commentId, Long userId) {
        Comment foundComment = baseCommentQuery()
                .leftJoin(comment.parent, parent).fetchJoin()
                .where(comment.id.eq(commentId))
                .fetchOne();

        if (foundComment == null) {
            throw new InvalidRequestException(ErrorCode.COMMENT_NOT_FOUND);
        }

        // 입력받은 userId와 조회한 userId 검증
        if (!foundComment.getUser().getId().equals(userId)) {
            throw new InvalidRequestException(ErrorCode.UNAUTHORIZED_ID);
        }

        return foundComment;
    }

    @Override
    public void delete(Comment deleteComment) {
        commentRepository.delete(deleteComment);
    }

    @Override
    public Comment findById(Long parentId) {
        Comment foundComment = baseCommentQuery()
                .where(comment.id.eq(parentId))
                .fetchOne();

        if (foundComment == null) {
            throw new InvalidRequestException(ErrorCode.COMMENT_NOT_FOUND);
        }

        return foundComment;
    }
}
