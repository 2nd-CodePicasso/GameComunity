package com.example.codePicasso.domain.exchange.repository;

import com.example.codePicasso.domain.exchange.entity.Exchange;
import com.example.codePicasso.domain.exchange.entity.TradeType;
import com.example.codePicasso.domain.exchange.service.ExchangeConnector;
import com.example.codePicasso.global.exception.base.NotFoundException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ExchangeConnectorImpl implements ExchangeConnector {
    private final ExchangeRepository exchangeRepository;

    @Override
    public Exchange save(Exchange exchange) {
        return exchangeRepository.save(exchange);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Exchange> findByTradeType(TradeType tradeType, Pageable pageable) {
        return exchangeRepository.findAllByTradeTypeAndIsCompleted(tradeType, false, pageable);
    }

    @Override
    public Page<Exchange> findByGameIdAndTradeType(Long gameId, TradeType tradeType, Pageable pageable) {
        return exchangeRepository.findByGameIdAndTradeTypeAndIsCompleted(gameId, tradeType, false, pageable);
    }

    @Override
    public Exchange findByIdAndIsCompleted(Long id) {
        return exchangeRepository.findByIdAndIsCompleted(id, false);
    }

    @Override
    public Exchange findById(Long exchangeId) {
        return exchangeRepository.findById(exchangeId).orElseThrow(() -> new NotFoundException(ErrorCode.EXCHANGE_NOT_FOUND));
    }
}
