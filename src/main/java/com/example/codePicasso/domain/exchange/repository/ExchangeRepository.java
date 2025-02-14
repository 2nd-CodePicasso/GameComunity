package com.example.codePicasso.domain.exchange.repository;

import com.example.codePicasso.domain.exchange.entity.Exchange;
import com.example.codePicasso.domain.exchange.entity.TradeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeRepository extends JpaRepository<Exchange, Long> {

    Page<Exchange> findByGameIdAndTradeType(Long gameId, TradeType tradeType, Pageable pageable);

    Page<Exchange> findAllByTradeType(TradeType tradeType, Pageable pageable);
}