package com.example.codePicasso.domain.exchange.service;

import com.example.codePicasso.domain.exchange.entity.TradeCount;
import com.example.codePicasso.domain.exchange.entity.TradeType;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public interface TradeCountConnector {

    TradeCount save(TradeCount tradeCount);

    Optional<TradeCount> findByGameIdAndTradeType(Long gameId, TradeType tradeType);

    void delete(TradeCount tradeCount);

    List<TradeCount> findTopNByTradeTypeOrderByCountDesc(TradeType tradeType, int topN);
}
