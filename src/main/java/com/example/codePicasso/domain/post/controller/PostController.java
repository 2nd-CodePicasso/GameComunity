package com.example.codePicasso.domain.post.controller;

import com.example.codePicasso.domain.post.dto.request.PostCreateRequest;
import com.example.codePicasso.domain.post.dto.request.PostUpdateRequest;
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
    public ResponseEntity<ApiResponse<PostResponse>> createPost(@RequestAttribute Long userId, @PathVariable Long gameId, @RequestBody PostCreateRequest request) {
        PostResponse response = postService.createPost(userId, gameId, request);
        return ApiResponse.created(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<GetGameIdAllPostsResponse>>> findPostByGameId(@PathVariable Long gameId) {
        List<GetGameIdAllPostsResponse> response = postService.findPostByGameId(gameId);
        return ApiResponse.success(response);
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<ApiResponse<List<PostResponse>>> findPostsByCategoryId(@PathVariable Long categoryId) {
        List<PostResponse> response = postService.findPostByCategoryId(categoryId);
        return ApiResponse.success(response);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostResponse>> findById(@PathVariable Long postId) {
        PostResponse response = postService.findById(postId);
        return ApiResponse.success(response);
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostResponse>> updatePost(@PathVariable Long postId, @RequestBody PostUpdateRequest request, @RequestAttribute Long userid) {
        PostResponse response = postService.updatePost(postId, request, userid);
        return ApiResponse.success(response);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable Long postId, @RequestAttribute Long userId) {
        postService.deletePost(postId, userId);
        return ApiResponse.noContent();
    }
}
