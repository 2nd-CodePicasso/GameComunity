package com.example.codePicasso.domain.exchange.repository;

import com.example.codePicasso.domain.exchange.entity.MyExchange;
import com.example.codePicasso.domain.exchange.entity.TradeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyExchangeRepository extends JpaRepository<MyExchange, Long> {
    List<MyExchange> findByUserId(Long userId);

    Page<MyExchange> findByUserIdAndExchange_TradeType(Long userId, TradeType tradeType, Pageable pageable);

    boolean existsByExchangeIdAndUserId(Long exchangeId, Long userId);

    MyExchange findByExchangeIdAndUserId(Long exchangeId, Long userId);
}
