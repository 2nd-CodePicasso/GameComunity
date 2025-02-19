package com.example.codePicasso.domain.comment.controller;

import com.example.codePicasso.domain.comment.dto.request.CommentRequest;
import com.example.codePicasso.domain.comment.dto.response.CommentListResponse;
import com.example.codePicasso.domain.comment.dto.response.CommentResponse;
import com.example.codePicasso.domain.comment.service.CommentService;
import com.example.codePicasso.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            @RequestAttribute Long userId,
            @RequestBody CommentRequest request
    ) {
        CommentResponse commentResponse = commentService.createComment(postId, userId, request.text());
        return ApiResponse.created(commentResponse);
    }

    @PostMapping("/{postId}/reply/{parentCommentId}")
    public ResponseEntity<ApiResponse<CommentResponse>> createReply(
            @PathVariable("postId") Long postId,
            @PathVariable("parentCommentId") Long parentCommentId,
            @RequestAttribute Long userId,
            @RequestBody
    ) {

    }


    /**
     * 댓글 조회
     * @param postId
     * @return
     */
    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<CommentListResponse>> findComment(
            @PathVariable("postId") Long postId
    ) {
        List<CommentResponse> response = commentService.findCommentByPostId(postId);
        return ApiResponse.success(CommentListResponse.builder().responses(response).build());
    }



    /**
     * 댓글 수정
     * @param commentId
     * @param userId (유저검증용)
     * @param request (text)
     * @return
     */
    @PatchMapping("/{commentId}")
    public ResponseEntity<ApiResponse<CommentResponse>> updateComment(
            @PathVariable("commentId") Long commentId,
            @RequestAttribute Long userId,
            @RequestBody CommentRequest request
    ) {
        CommentResponse commentResponse = commentService.updateComment(commentId, userId, request.text());
        return ApiResponse.success(commentResponse);
    }



    /**
     * 댓글 삭제
     * @param commentId
     * @param userId
     * @return
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(
            @PathVariable("commentId") Long commentId,
            @RequestAttribute Long userId
    ) {
        commentService.deleteComment(commentId, userId);
        return ApiResponse.noContent();
    }


}
