package com.example.codePicasso.domain.comment.service;

import com.example.codePicasso.domain.comment.dto.request.CommentRequest;
import com.example.codePicasso.domain.comment.dto.response.CommentListResponse;
import com.example.codePicasso.domain.comment.dto.response.CommentResponse;
import com.example.codePicasso.domain.comment.dto.response.ReplyResponse;
import com.example.codePicasso.domain.comment.entity.Comment;
import com.example.codePicasso.domain.post.entity.Post;
import com.example.codePicasso.domain.post.service.PostConnector;
import com.example.codePicasso.domain.user.entity.User;
import com.example.codePicasso.domain.user.service.UserConnector;
import com.example.codePicasso.global.common.DtoFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentConnector commentConnector;
    private final PostConnector postConnector;
    private final UserConnector userConnector;

    // 댓글 생성
    @Transactional
    public CommentResponse createComment(Long postId, Long userId, CommentRequest request) {
        Post post = postConnector.findById(postId);
        User user = userConnector.findById(userId);

        Comment createComment = CommentRequest.toEntityForComment(post, user, request.text());

        Comment saveCategory = commentConnector.save(createComment);
        return DtoFactory.toCommentDto(saveCategory);
    }

    // 대댓글 생성
    @Transactional
    public ReplyResponse createReply(Long postId, Long parentId, Long userId, CommentRequest request) {
        Post post = postConnector.findById(postId);
        User user = userConnector.findById(userId);
        Comment parentComment = commentConnector.findById(parentId);

        Comment createReply = CommentRequest.toEntityForReply(post, user, parentComment, request.text());
        parentComment.addReplies(createReply);

        commentConnector.save(createReply);
        return DtoFactory.toReplyDto(createReply);
    }

    // 댓글, 대댓글 전체 조회
    public CommentListResponse findAllByPostId(Long postId) {
        List<CommentResponse> commentResponses = commentConnector.findAllByPostId(postId).stream()
                .map(DtoFactory::toCommentDto).toList();
        return CommentListResponse.builder()
                .commentResponses(commentResponses)
                .build();
    }

    // 댓글, 대댓글 수정
    @Transactional
    public CommentResponse updateComment(Long commentId, Long userId, CommentRequest request) {
        Comment foundComment = commentConnector.findByIdAndUserId(commentId, userId);

        foundComment.updateComment(request.text());
        return DtoFactory.toCommentDto(foundComment);
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId, Long userId) {
        Comment deleteComment = commentConnector.findByIdAndUserId(commentId, userId);
        commentConnector.delete(deleteComment);
    }



}
