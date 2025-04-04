package com.example.codePicasso.domain.review.dto.response;

import lombok.Builder;
import org.springframework.data.domain.Page;

@Builder
public record ReviewListResponse(
        Page<ReviewResponse> reviewPageResponse
) {
}
