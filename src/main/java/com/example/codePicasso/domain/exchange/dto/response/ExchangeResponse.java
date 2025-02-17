package com.example.codePicasso.domain.exchange.dto.response;

import com.example.codePicasso.domain.exchange.entity.Exchange;
import lombok.Builder;

@Builder
public record ExchangeResponse(
        Long id,
        Long userId,
        Long gameId,
        String title,
        int price,
        String description,
        int quantity
) {
    public static ExchangeResponse fromEntity(Exchange exchange) {
        return ExchangeResponse.builder()
                .id(exchange.getId())
                .userId(exchange.getUser().getId())
                .gameId(exchange.getGame().getId())
                .title(exchange.getTitle())
                .price(exchange.getPrice())
                .description(exchange.getDescription())
                .quantity(exchange.getQuantity())
                .build();
    }
}
