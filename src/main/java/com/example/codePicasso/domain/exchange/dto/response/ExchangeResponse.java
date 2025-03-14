package com.example.codePicasso.domain.exchange.dto.response;

import com.example.codePicasso.domain.exchange.entity.TradeType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;

@Builder
public record ExchangeResponse(
        Long id,
        Long userId,
        Long gameId,
        String title,
        int price,
        String description,
        int quantity,
        String contact,
        TradeType tradeType,
        boolean isCompleted
) {
    @QueryProjection
    public ExchangeResponse {
    }
}
