package com.example.codePicasso.domain.review.dto.response;

import lombok.Builder;

@Builder
public record ReviewResponse(
        Long id,
        Long exchangeId,
        String nickname,
        int rating,
        String review
) {
}
