package com.example.codePicasso.domain.post.controller;

import com.example.codePicasso.domain.post.dto.request.PostRequest;
import com.example.codePicasso.domain.post.dto.response.PostListResponse;
import com.example.codePicasso.domain.post.dto.response.PostResponse;
import com.example.codePicasso.domain.post.service.PostService;
import com.example.codePicasso.global.common.ApiResponse;
import com.example.codePicasso.global.common.CustomUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    /**
     * 게시글 생성
     *
     * @param user
     * @param request (categoryId, title, description)
     * @return 생성된 게시글
     */
    @PostMapping
    public ResponseEntity<ApiResponse<PostResponse>> createPost(
            @AuthenticationPrincipal CustomUser user,
            @Valid @RequestBody PostRequest request
    ) {
        PostResponse response = postService.createPost(user.getUserId(), request);
        return ApiResponse.created(response);
    }

    /**
     * gameId로 게시글 전체 조회
     *
     * @param gameId
     * @return gameId 내 모든 게시글 조회
     */
    @GetMapping("/hi/games/{gameId}")
    public ResponseEntity<ApiResponse<PostListResponse>> findPostByGameId(
            @PathVariable("gameId") Long gameId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        PostListResponse postListResponse = postService.findAllByGameId(gameId, page, size);
        return ApiResponse.success(postListResponse);
    }

//    /**
//     * 카테고리별 게시글 전체 조회
//     *
//     * @param categoryId
//     * @return categoryId 내 모든 게시글 조회
//     */
//    @GetMapping("/hi/categories/{categoryId}")
//    public ResponseEntity<ApiResponse<PostListResponse>> findPostsByCategoryId(
//            @PathVariable("categoryId") Long categoryId,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "20") int size
//    ) {
//        PostListResponse postListResponse = postService.findAllByCategoryId(categoryId, page, size);
//        return ApiResponse.success(postListResponse);
//    }
//
//    /**
//     * 추천글만 조회하기
//     *
//     * @return
//     */
//    @GetMapping("/hi/games/{gameId}/recommended")
//    public ResponseEntity<ApiResponse<PostListResponse>> findRecommendedPost(
//            @PathVariable Long gameId,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "20") int size
//    ) {
//        PostListResponse postListResponse = postService.findRecommendedPost(gameId, page, size);
//        return ApiResponse.success(postListResponse);
//    }

    /**
     * 개별 게시글 조회
     *
     * @param postId
     * @return 개별 게시물
     */
    @GetMapping("/hi/{postId}")
    public ResponseEntity<ApiResponse<PostResponse>> findById(
            @PathVariable("postId") Long postId
    ) {
        PostResponse response = postService.findById(postId);
        return ApiResponse.success(response);
    }

    /**
     * 게시글 수정
     *
     * @param postId
     * @param user
     * @param request (categoryId, title, description)
     * @return 수정된 게시물
     */
    @PatchMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostResponse>> updatePost(
            @PathVariable("postId") Long postId,
            @AuthenticationPrincipal CustomUser user,
            @Valid @RequestBody PostRequest request
    ) {
        PostResponse response = postService.updatePost(postId, user.getUserId(), request);
        return ApiResponse.success(response);
    }

    /**
     * 게시물 삭제
     *
     * @param postId
     * @param user
     * @return return 없음
     */
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(
            @PathVariable("postId") Long postId,
            @AuthenticationPrincipal CustomUser user
    ) {
        postService.deletePost(postId, user.getUserId());
        return ApiResponse.noContent();
    }
}
