package com.example.codePicasso.domain.exchange.service;

import com.example.codePicasso.domain.exchange.entity.Exchange;
import com.example.codePicasso.domain.exchange.entity.TradeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public interface ExchangeConnector {

    Exchange save(Exchange exchange);

//    List<Exchange> findAll();

    Page<Exchange> findByTradeType(TradeType tradeType, Pageable pageable);

    Page<Exchange> findByGameIdAndTradeType(Long gameId, TradeType tradeType, Pageable pageable);

    Exchange findById(Long id);

    void deleteById(Long id);
}
