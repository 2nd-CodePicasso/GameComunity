package com.example.codePicasso.domain.exchange.dto.request;

import com.example.codePicasso.domain.exchange.entity.StatusType;

public record PutMyExchangeRequest(
        StatusType statusType
) {
}
