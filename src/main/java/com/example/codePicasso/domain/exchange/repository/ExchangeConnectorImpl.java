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

@Component
@RequiredArgsConstructor
public class ExchangeConnectorImpl implements ExchangeConnector {
    private final ExchangeRepository exchangeRepository;

    @Override
    public Exchange save(Exchange exchange) {
        return exchangeRepository.save(exchange);
    }

//    @Override
//    public List<Exchange> findAll() {
//        return exchangeRepository.findAll();
//    }

    @Override
    public Page<Exchange> findByTradeType(TradeType tradeType, Pageable pageable) {
        return exchangeRepository.findAllByTradeType(tradeType, pageable);
    }

    @Override
    public Page<Exchange> findByGameIdAndTradeType(Long gameId, TradeType tradeType, Pageable pageable) {
        return exchangeRepository.findByGameIdAndTradeType(gameId, tradeType, pageable);
    }

    @Override
    public Exchange findById(Long id) {
        return exchangeRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorCode.EXCHANGE_NOT_FOUND));
    }

    @Override
    public void deleteById(Long id) {
        exchangeRepository.deleteById(id);
    }


}
