package com.example.codePicasso.domain.comment.controller;

import com.example.codePicasso.domain.comment.dto.request.CommentRequest;
import com.example.codePicasso.domain.comment.dto.response.CommentListResponse;
import com.example.codePicasso.domain.comment.dto.response.CommentResponse;
import com.example.codePicasso.domain.comment.service.CommentService;
import com.example.codePicasso.global.common.ApiResponse;
import com.example.codePicasso.global.common.CustomUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    /**
     * 댓글 생성
     * @param postId
     * @param request (text)
     * @return 생성된 댓글
     */
    @PostMapping("/{postId}")
    public ResponseEntity<ApiResponse<CommentResponse>> createComment(
            @PathVariable("postId") Long postId,
            @AuthenticationPrincipal CustomUser user,
            @Valid @RequestBody CommentRequest request
    ) {
        CommentResponse commentResponse = commentService.createComment(postId, user.getUserId(), request);
        return ApiResponse.created(commentResponse);
    }

    /**
     * 대댓글 생성
     */
    @PostMapping("/reply/{parentId}")
    public ResponseEntity<ApiResponse<CommentResponse>> createReply(
            @PathVariable("parentId") Long parentId,
            @AuthenticationPrincipal CustomUser user,
            @Valid @RequestBody CommentRequest request
    ) {
        CommentResponse replyResponse = commentService.createReply(parentId, user.getUserId(), request);
        return ApiResponse.created(replyResponse);
    }


    /**
     * 댓글 조회
     * @param postId
     * @return
     */
    @GetMapping("/hi/{postId}")
    public ResponseEntity<ApiResponse<CommentListResponse>> findComment(
            @PathVariable("postId") Long postId
    ) {
        CommentListResponse responses = commentService.findAllByPostId(postId);
        return ApiResponse.success(responses);
    }


    /**
     * 댓글 수정
     * @param commentId
     * @param user (유저검증용)
     * @param request (text)
     * @return
     */
    @PatchMapping("/{commentId}")
    public ResponseEntity<ApiResponse<CommentResponse>> updateComment(
            @PathVariable("commentId") Long commentId,
            @AuthenticationPrincipal CustomUser user,
            @Valid @RequestBody CommentRequest request
    ) {
        CommentResponse commentResponse = commentService.updateComment(commentId, user.getUserId(), request);
        return ApiResponse.success(commentResponse);
    }


    /**
     * 댓글 삭제
     * @param commentId
     * @param user
     * @return
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(
            @PathVariable("commentId") Long commentId,
            @AuthenticationPrincipal CustomUser user
    ) {
        commentService.deleteComment(commentId, user.getUserId());
        return ApiResponse.noContent();
    }


}
