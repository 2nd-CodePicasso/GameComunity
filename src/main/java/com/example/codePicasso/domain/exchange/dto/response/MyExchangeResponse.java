package com.example.codePicasso.domain.exchange.dto.response;

import com.example.codePicasso.domain.exchange.entity.StatusType;
import lombok.Builder;

@Builder
public record MyExchangeResponse(
        Long id,
        Long exchangeId,
        Long userId,
        String contact,
        StatusType statusType
) {
}
