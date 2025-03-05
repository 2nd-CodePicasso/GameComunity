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

    @PostMapping("/reviews")
    public ResponseEntity<ApiResponse<ReviewResponse>> createReview(
            @PathVariable TradeType tradeType,
            @PathVariable Long exchangeId,
            @AuthenticationPrincipal CustomUser user,
            @RequestBody ReviewRequest request
    ) {
        return ApiResponse.created(reviewService.createReview(exchangeId, user.getUserId(), request));
    }

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
        return ApiResponse.success(reviewService.getReviewById(exchangeId, reviewId));
    }

    @PatchMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<ReviewResponse>> updateReview(
            @PathVariable TradeType tradeType,
            @PathVariable Long exchangeId,
            @PathVariable Long reviewId,
            @AuthenticationPrincipal CustomUser user,
            @RequestBody ReviewRequest request
    ) {
        return ApiResponse.success(reviewService.updateReview(exchangeId, reviewId, user.getUserId(), request));
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
