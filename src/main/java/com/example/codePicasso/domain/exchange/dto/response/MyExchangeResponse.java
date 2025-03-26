package com.example.codePicasso.domain.exchange.dto.response;

import com.example.codePicasso.domain.exchange.entity.StatusType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;

@Builder
public record MyExchangeResponse(
        Long id,
        Long exchangeId,
        String nickname,
        String contact,
        StatusType statusType
) {
    @QueryProjection
    public MyExchangeResponse(
            Long id,
            Long exchangeId,
            String nickname,
            String contact,
            StatusType statusType
    ) {
        this.id = id;
        this.exchangeId = exchangeId;
        this.nickname = nickname;
        this.contact = contact;
        this.statusType = statusType;
    }
}
