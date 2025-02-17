package com.example.codePicasso.domain.exchange.dto.response;

import lombok.Builder;
import org.springframework.data.domain.Page;

@Builder
public record ExchangeListResponse(
        Page<ExchangeResponse> exchangePageResponse
) {
}
