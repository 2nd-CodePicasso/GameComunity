package com.example.codePicasso.domain.recommend.controller;

import com.example.codePicasso.domain.recommend.service.RecommendService;
import com.example.codePicasso.global.common.ApiResponse;
import com.example.codePicasso.global.common.CustomUser;
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
    public ResponseEntity<ApiResponse<Integer>> createRecommend(
            @PathVariable Long postId,
            @AuthenticationPrincipal CustomUser user
    ) {
        Integer response = recommendService.doRecommend(postId, user.getUserId());
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
            @AuthenticationPrincipal CustomUser user
    ) {
        recommendService.undoRecommend(postId, user.getUserId());
        return ApiResponse.noContentAndSendMessage("추천이 취소되었습니다.");
    }
}
