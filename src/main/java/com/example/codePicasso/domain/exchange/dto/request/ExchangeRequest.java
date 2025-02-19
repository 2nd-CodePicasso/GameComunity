package com.example.codePicasso.domain.exchange.dto.request;

import com.example.codePicasso.domain.exchange.entity.Exchange;
import com.example.codePicasso.domain.exchange.entity.TradeType;
import com.example.codePicasso.domain.game.entity.Game;
import com.example.codePicasso.domain.user.entity.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ExchangeRequest(
        @NotNull(message = "게임을 입력하세요.")
        Long gameId,
        @NotNull(message = "제목을 입력하세요.")
        @Size(max = 50)
        String title,
        @NotNull(message = "가격을 입력하세요.")
        int price,
        String description,
        @NotNull(message = "수량을 입력하세요.")
        int quantity
) {
    public Exchange toEntity(User user, Game game, TradeType tradeType) {
        return Exchange.builder()
                .user(user)
                .game(game)
                .title(title)
                .price(price)
                .description(description)
                .quantity(quantity)
                .tradeType(tradeType)
                .build();
    }
}
