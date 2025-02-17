package com.example.codePicasso.domain.exchange.dto.request;

import com.example.codePicasso.domain.exchange.entity.Exchange;
import com.example.codePicasso.domain.exchange.entity.MyExchange;
import com.example.codePicasso.domain.users.entity.User;
import jakarta.validation.constraints.NotNull;

public record MyExchangeRequest (
        @NotNull(message = "연락처를 입력하세요.")
        int contact
)
{
    public MyExchange toEntity(Exchange exchange, User user) {
        return MyExchange.builder()
                .exchange(exchange)
                .user(user)
                .contact(contact)
                .build();
    }
}
