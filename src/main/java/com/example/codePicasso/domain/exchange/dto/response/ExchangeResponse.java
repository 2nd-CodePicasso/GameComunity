package com.example.codePicasso.domain.exchange.dto.response;

import com.example.codePicasso.domain.exchange.entity.TradeType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;

@Builder
public record ExchangeResponse(
        Long id,
        Long gameId,
        String nickname,
        String title,
        int price,
        String description,
        int quantity,
        String contact,
        TradeType tradeType,
        boolean isCompleted
) {
    @QueryProjection
    public ExchangeResponse(
            Long id,
            Long gameId,
            String nickname,
            String title,
            int price,
            String description,
            int quantity,
            String contact,
            TradeType tradeType,
            boolean isCompleted
    ) {
        this.id = id;
        this.gameId = gameId;
        this.nickname = nickname;
        this.title = title;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
        this.contact = contact;
        this.tradeType = tradeType;
        this.isCompleted = isCompleted;
    }
}
