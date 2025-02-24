package com.example.codePicasso.domain.exchange.repository;

import com.example.codePicasso.domain.exchange.entity.MyExchange;
import com.example.codePicasso.domain.exchange.entity.TradeType;
import com.example.codePicasso.domain.exchange.service.MyExchangeConnector;
import com.example.codePicasso.global.exception.base.NotFoundException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MyExchangeConnectorImpl implements MyExchangeConnector {
    private final MyExchangeRepository myExchangeRepository;

    @Override
    public MyExchange save(MyExchange myExchange) {
        return myExchangeRepository.save(myExchange);
    }

    @Override
    public List<MyExchange> findByUserId(Long userId) {
        return myExchangeRepository.findByUserId(userId);
    }

    @Override
    public Page<MyExchange> findByUserIdAndTradeType(Long userId, TradeType tradeType, Pageable pageable) {
        return myExchangeRepository.findByUserIdAndExchange_TradeType(userId, tradeType, pageable);
    }

    @Override
    public MyExchange findById(Long myExchangeId) {
        return myExchangeRepository.findById(myExchangeId).orElseThrow(() -> new NotFoundException(ErrorCode.MYEXCHANGE_NOT_FOUND));
    }
}
