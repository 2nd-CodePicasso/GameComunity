package com.example.codePicasso.domain.exchange.dto.response;

import com.example.codePicasso.domain.exchange.entity.Exchange;
import com.example.codePicasso.domain.users.entity.User;
import lombok.Builder;

@Builder
public record MyExchangeResponse (
        Exchange exchange,
        User user,
        int contact
) {
}
