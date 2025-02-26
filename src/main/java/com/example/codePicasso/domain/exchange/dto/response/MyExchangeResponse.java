package com.example.codePicasso.domain.exchange.dto.response;

import com.example.codePicasso.domain.exchange.entity.StatusType;
import lombok.Builder;

@Builder
public record MyExchangeResponse (
        Long exchangeId,
        Long userId,
        String contact,
        StatusType statustype
) {
}
