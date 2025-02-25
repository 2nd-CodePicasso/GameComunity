package com.example.codePicasso.domain.exchange.dto.response;

import com.example.codePicasso.domain.exchange.entity.Exchange;
import com.example.codePicasso.domain.user.entity.User;
import lombok.Builder;

@Builder
public record MyExchangeResponse (
        Exchange exchange,
        User user,
        String contact
) {
}
