package com.example.codePicasso.domain.exchange.controller;

import com.example.codePicasso.domain.exchange.entity.TradeCount;
import com.example.codePicasso.domain.exchange.entity.TradeType;
import com.example.codePicasso.domain.exchange.service.ExchangeRankingService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exchanges")
public class ExchangeRankingController {

    private final ExchangeRankingService exchangeRankingService;


    @GetMapping("/top-rankings")
    public ResponseEntity<List<TradeCount>> getTopRanking(
        @RequestParam int topN,
        @RequestParam TradeType tradeType
    ) {
        List<TradeCount> topRanking = exchangeRankingService.getTopRanking(topN, tradeType);
        return ResponseEntity.ok(topRanking);
    }

    // 특정 게임의 전체 기간 거래량 조회
    @GetMapping("/total-trade-count")
    public ResponseEntity<Long> getTotalTradeCount(
        @RequestParam Long gameId,
        @RequestParam TradeType tradeType) {
        Long totalTradeCount = exchangeRankingService.getTotalTradeCount(gameId, tradeType);
        return ResponseEntity.ok(totalTradeCount);
    }

    // 특정 게임의 기간별 거래량 조회
    @GetMapping("/trade-count")
    public ResponseEntity<Map<String, Long>> getTradeCountByTimePeriod(
        @RequestParam Long gameId,
        @RequestParam boolean isHourly,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
        @RequestParam TradeType tradeType) {
        Map<String, Long> tradeCounts = exchangeRankingService.getTradeCountByTimePeriod(gameId, startDate, endDate, isHourly, tradeType);
        return ResponseEntity.ok(tradeCounts);
    }

    // 게임 타이틀로 게임 ID 조회
    @GetMapping("/game-id")
    public ResponseEntity<Long> getGameIdByTitle(@RequestParam String gameTitle) {
        Long gameId = exchangeRankingService.getGameIdByTitle(gameTitle);
        return ResponseEntity.ok(gameId);
    }
}
