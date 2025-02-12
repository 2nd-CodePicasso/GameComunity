package com.example.codePicasso.domain.exchanges.repository;

import com.example.codePicasso.domain.exchanges.entity.Exchange;
import com.example.codePicasso.domain.exchanges.entity.TradeType;
import com.example.codePicasso.domain.exchanges.service.ExchangeConnector;
import com.example.codePicasso.global.exception.base.NotFoundException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ExchangeConnectorImpl implements ExchangeConnector {
    private final ExchangeRepository exchangeRepository;

    @Override
    public Exchange save(Exchange exchange) {
        return exchangeRepository.save(exchange);
    }

    @Override
    public List<Exchange> findByTradeType(TradeType tradeType) {
        return exchangeRepository.findAllByTradeType(tradeType);
    }

//    @Override
//    public List<Exchange> findAll() {
//        return exchangeRepository.findAll();
//    }

    @Override
    public List<Exchange> findByGameIdAndTradeType(Long gameId, TradeType tradeType) {
        return exchangeRepository.findByGameIdAndTradeType(gameId, tradeType);
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
