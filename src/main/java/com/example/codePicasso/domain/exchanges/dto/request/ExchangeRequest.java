package com.example.codePicasso.domain.exchanges.dto.request;

import com.example.codePicasso.domain.exchanges.entity.Exchange;
import com.example.codePicasso.domain.games.entity.Games;
import com.example.codePicasso.domain.users.entity.User;

public record ExchangeRequest(
        Long id,
        Long userId,
        Long gameId,
        String title,
        int price,
        String description,
        int quantity
) {
    public Exchange toEntity(User user, Games games) {
        return Exchange.builder()
                .user(user)
                .games(games)
                .title(title)
                .price(price)
                .description(description)
                .quantity(quantity)
                .build();
    }
}
