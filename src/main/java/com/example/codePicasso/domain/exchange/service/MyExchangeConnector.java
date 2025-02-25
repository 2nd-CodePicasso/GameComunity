package com.example.codePicasso.domain.exchange.service;

import com.example.codePicasso.domain.exchange.entity.MyExchange;
import com.example.codePicasso.domain.exchange.entity.TradeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MyExchangeConnector {

    MyExchange save(MyExchange myExchange);

    List<MyExchange> findByUserId(Long userId);

    Page<MyExchange> findByUserIdAndTradeType(Long userId, TradeType tradeType, Pageable pageable);

    MyExchange findById(Long myExchangeId);

    boolean existByExchangeIdAndUserId(Long exchangeId, Long userId);
}
