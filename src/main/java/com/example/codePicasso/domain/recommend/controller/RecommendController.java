package com.example.codePicasso.domain.recommend.controller;

import com.example.codePicasso.domain.recommend.dto.response.RecommendResponse;
import com.example.codePicasso.domain.recommend.service.RecommendService;
import com.example.codePicasso.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
