package com.example.codePicasso.domain.post.controller;

import com.example.codePicasso.domain.post.dto.request.PostRequest;
import com.example.codePicasso.domain.post.dto.response.PostListResponse;
import com.example.codePicasso.domain.post.dto.response.PostResponse;
import com.example.codePicasso.domain.post.service.PostService;
import com.example.codePicasso.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/games/{gameId}/posts")
public class PostController {

    private final PostService postService;

    /**
     * 게시글 생성
     * @param userId
     * @param gameId
     * @param request (categoryId, title, description)
     * @return 생성된 게시글
     */
    @PostMapping
    public ResponseEntity<ApiResponse<PostResponse>> createPost(
            @RequestAttribute Long userId,
            @PathVariable("gameId") Long gameId,
            PostRequest request
    ) {
        PostResponse response = postService.createPost(userId, gameId, request.categoryId(), request.title(), request.description());
        return ApiResponse.created(response);
    }

    /**
     * gameId로 게시글 전체 조회
     * @param gameId
     * @return gameId 내 모든 게시글 조회
     */
    @GetMapping
    public ResponseEntity<ApiResponse<PostListResponse>> findPostByGameId(
            @PathVariable("gameId") Long gameId
    ) {
        List<PostResponse> response = postService.findPostByGameId(gameId);
        return ApiResponse.success(PostListResponse.builder().response(response).build());
    }

    /**
     * 카테고리별 게시글 전체 조회
     * @param categoryId
     * @return categoryId 내 모든 게시글 조회
     */
    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<ApiResponse<PostListResponse>> findPostsByCategoryId(
            @PathVariable("gameId") Long gameId,
            @PathVariable("categoryId") Long categoryId
    ) {
        List<PostResponse> response = postService.findPostByCategoryId(gameId, categoryId);
        return ApiResponse.success(PostListResponse.builder().response(response).build());
    }

    /**
     * 개별 게시글 조회
     * @param postId
     * @return 개별 게시물
     */
    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostResponse>> findPostById(
            @PathVariable("gameId") Long gameId,
            @PathVariable("postId") Long postId
    ) {
        PostResponse response = postService.findPostById(gameId, postId);
        return ApiResponse.success(response);
    }

    /**
     * 게시글 수정
     * @param postId
     * @param userid
     * @param request (categoryId, title, description)
     * @return 수정된 게시물
     */
    @PatchMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostResponse>> updatePost(
            @PathVariable("gameId") Long gameId,
            @PathVariable("postId") Long postId,
            @RequestAttribute Long userid, PostRequest request
    ) {
        PostResponse response = postService.updatePost(gameId, postId, userid, request.categoryId(), request.title(), request.description());
        return ApiResponse.success(response);
    }

    /**
     * 게시물 삭제
     * @param postId
     * @param userId
     * @return return 없음
     */
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(
            @PathVariable("gameId") Long gameId,
            @PathVariable("postId") Long postId,
            @RequestAttribute Long userId
    ) {
        postService.deletePost(gameId, postId, userId);
        return ApiResponse.noContent();
    }
}
