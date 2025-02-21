package com.example.codePicasso.domain.recommend.controller;

import com.example.codePicasso.domain.recommend.dto.response.RecommendResponse;
import com.example.codePicasso.domain.recommend.service.RecommendService;
import com.example.codePicasso.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recommends")
public class RecommendController {
    private final RecommendService recommendService;

    @PostMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse<RecommendResponse>> createRecommend(
            @PathVariable Long postId,
            @AuthenticationPrincipal Long userId
    ) {
        RecommendResponse response = recommendService.doRecommend(postId, userId);
        return ApiResponse.success(response);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse<Integer>> getRecommendCount(
            @PathVariable Long postId
    ) {
        Integer count = recommendService.countRecommendOfPost(postId);
        return ApiResponse.success(count);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse<Void>> deleteRecommend(
            @PathVariable Long postId,
            @AuthenticationPrincipal Long userId
    ) {
        recommendService.undoRecommend(postId, userId);
        return ApiResponse.noContentAndSendMessage("추천이 취소되었습니다.");
    }
}
