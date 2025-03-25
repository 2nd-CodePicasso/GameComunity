package com.example.codePicasso.domain.exchange.service;

import com.example.codePicasso.domain.exchange.dto.response.ExchangeResponse;
import com.example.codePicasso.domain.exchange.entity.Exchange;
import com.example.codePicasso.domain.exchange.entity.TradeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public interface ExchangeConnector {
    Exchange save(Exchange exchange);

    Page<ExchangeResponse> findByTradeType(TradeType tradeType, Pageable pageable);

    Page<ExchangeResponse> findByGameIdAndTradeType(Long gameId, TradeType tradeType, Pageable pageable);

    ExchangeResponse findByIdAndIsCompleted(Long id);

    Exchange findById(Long exchangeId);
}
