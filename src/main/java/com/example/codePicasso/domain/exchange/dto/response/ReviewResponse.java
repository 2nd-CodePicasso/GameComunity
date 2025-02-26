package com.example.codePicasso.domain.exchange.dto.response;

import lombok.Builder;

@Builder
public record ReviewResponse(
        Long id,
        Long exchangeId,
        Long userId,
        int rating,
        String review
) {
}
