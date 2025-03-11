package com.example.codePicasso.domain.exchange.service;

import com.example.codePicasso.domain.exchange.entity.TradeCount;
import com.example.codePicasso.domain.exchange.entity.TradeType;
import java.util.List;
import java.util.Optional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ExchangeRankingService {

    private final TradeCountConnector tradeCountConnector;

    public ExchangeRankingService(TradeCountConnector tradeCountConnector) {
        this.tradeCountConnector = tradeCountConnector;
    }

    @Cacheable(value = "tradeCounts", key = "#gameId + ':' + #tradeType")
    public TradeCount getTradeCount(Long gameId, TradeType tradeType) {
        return tradeCountConnector.findBygameId(gameId, tradeType)
            .orElse(new TradeCount(gameId, 0L));
    }

    @Cacheable(value = "tradeCounts", key = "#tradeCount.gameId")
    public TradeCount updateTradeCount(TradeCount tradeCount) {
        TradeCount updatedTradeCount = tradeCountConnector.save(tradeCount);
        String cacheKey = tradeCount.getGameId() + ":" + tradeCount.getTradeType();
        return updatedTradeCount;
    }

    @CacheEvict(value = "tradeCounts", key = "#gameId + ':' + #tradeType")
    public void deleteTradeCount(Long gameId, TradeType tradeType) {
        Optional<TradeCount> tradeCountOpt = tradeCountConnector.findByGameIdAndTradeType(gameId, tradeType);
        tradeCountOpt.ifPresent(tradeCountConnector::delete);
    }

    @Cacheable(value = "topRankings", key = "#topN + ':' + #tradeType")
    public List<TradeCount> getTopRanking(int topN, TradeType tradeType) {
        return tradeCountConnector.findTopNByTradeTypeOrderByCountDesc(tradeType, topN);
    }
}
