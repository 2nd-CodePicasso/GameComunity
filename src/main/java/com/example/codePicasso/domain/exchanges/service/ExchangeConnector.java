package com.example.codePicasso.domain.exchanges.service;

import com.example.codePicasso.domain.exchanges.entity.Exchange;
import com.example.codePicasso.domain.exchanges.entity.TradeType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ExchangeConnector {

    Exchange save(Exchange exchange);

//    List<Exchange> findAll();

    List<Exchange> findByTradeType(TradeType tradeType);

    List<Exchange> findByGameIdAndTradeType(Long gameId, TradeType tradeType);

    Exchange findById(Long id);

    void deleteById(Long id);
}
