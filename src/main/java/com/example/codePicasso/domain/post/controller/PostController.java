package com.example.codePicasso.domain.post.controller;

import com.example.codePicasso.domain.post.dto.request.PostRequest;
import com.example.codePicasso.domain.post.dto.response.GetGameIdAllPostsResponse;
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
     * @param toDto
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

    @GetMapping
    public ResponseEntity<ApiResponse<List<GetGameIdAllPostsResponse>>> findPostByGameId(
            @PathVariable("gameId") Long gameId
    ) {
        List<GetGameIdAllPostsResponse> response = postService.findPostByGameId(gameId);
        return ApiResponse.success(response);
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<ApiResponse<List<PostResponse>>> findPostsByCategoryId(
            @PathVariable("categoryId") Long categoryId
    ) {
        List<PostResponse> response = postService.findPostByCategoryId(categoryId);
        return ApiResponse.success(response);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostResponse>> findPostById(
            @PathVariable("postId") Long postId
    ) {
        PostResponse response = postService.findPostById(postId);
        return ApiResponse.success(response);
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostResponse>> updatePost(
            @PathVariable("postId") Long postId, @RequestAttribute Long userid, PostRequest request
    ) {
        PostResponse response = postService.updatePost(postId, userid, request.categoryId(), request.title(), request.description());
        return ApiResponse.success(response);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(
            @PathVariable("postId") Long postId,
            @RequestAttribute Long userId
    ) {
        postService.deletePost(postId, userId);
        return ApiResponse.noContent();
    }
}
