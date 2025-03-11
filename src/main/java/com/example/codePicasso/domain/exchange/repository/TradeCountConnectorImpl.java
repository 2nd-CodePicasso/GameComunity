package com.example.codePicasso.domain.exchange.repository;

import com.example.codePicasso.domain.exchange.entity.TradeCount;
import com.example.codePicasso.domain.exchange.entity.TradeType;
import com.example.codePicasso.domain.exchange.service.TradeCountConnector;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TradeCountConnectorImpl implements TradeCountConnector {
    private final TradeCountRepository tradeCountRepository;

    @Override
    public TradeCount save(TradeCount tradeCount) {
        return tradeCountRepository.save(tradeCount);
    }

    @Override
    public Optional<TradeCount> findByGameIdAndTradeType(Long gameId, TradeType tradeType) {
        return tradeCountRepository.findByGameIdAndTradeType(gameId, tradeType);
    }

    @Override
    public void delete(TradeCount tradeCount) {
        tradeCountRepository.delete(tradeCount);
    }

    @Override
    public List<TradeCount> findTopNByTradeTypeOrderByCountDesc(TradeType tradeType, int topN) {
        Pageable pageable = PageRequest.of(0, topN);
        return tradeCountRepository.findTopNByTradeTypeOrderByCountDesc(tradeType, pageable);
    }
}
