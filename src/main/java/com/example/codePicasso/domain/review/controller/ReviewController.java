package com.example.codePicasso.domain.review.controller;

import com.example.codePicasso.domain.exchange.entity.TradeType;
import com.example.codePicasso.domain.review.dto.request.ReviewRequest;
import com.example.codePicasso.domain.review.dto.response.ReviewListResponse;
import com.example.codePicasso.domain.review.dto.response.ReviewResponse;
import com.example.codePicasso.domain.review.service.ReviewService;
import com.example.codePicasso.global.common.ApiResponse;
import com.example.codePicasso.global.common.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exchanges/{tradeType}/{exchangeId}/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ApiResponse<ReviewResponse>> createReview(
            @PathVariable TradeType tradeType,
            @PathVariable Long exchangeId,
            @AuthenticationPrincipal CustomUser user,
            @RequestBody ReviewRequest request
    ) {
        ReviewResponse response = reviewService.createReview(exchangeId, user.getUserId(), request);

        return ApiResponse.created(response);
    }

    // Todo return 방법 통일 필요
    @GetMapping("/list")
    public ResponseEntity<ApiResponse<ReviewListResponse>> getAllReview(
            @PathVariable TradeType tradeType,
            @PathVariable Long exchangeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<ReviewResponse> reviews = reviewService.getReviews(exchangeId, page, size);

        return ApiResponse.success(ReviewListResponse.builder().reviewPageResponse(reviews).build());
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<ReviewResponse>> getReviewById(
            @PathVariable TradeType tradeType,
            @PathVariable Long exchangeId,
            @PathVariable Long reviewId
    ) {
        ReviewResponse response = reviewService.getReviewById(exchangeId, reviewId);

        return ApiResponse.success(response);
    }

    @PatchMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<ReviewResponse>> updateReview(
            @PathVariable TradeType tradeType,
            @PathVariable Long exchangeId,
            @PathVariable Long reviewId,
            @AuthenticationPrincipal CustomUser user,
            @RequestBody ReviewRequest request
    ) {
        ReviewResponse response = reviewService.updateReview(exchangeId, reviewId, user.getUserId(), request);

        return ApiResponse.success(response);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<Void>> deleteReview(
            @PathVariable TradeType tradeType,
            @PathVariable Long exchangeId,
            @PathVariable Long reviewId,
            @AuthenticationPrincipal CustomUser user
    ) {
        reviewService.deleteReview(exchangeId, reviewId, user.getUserId());

        return ApiResponse.noContent();
    }
}
