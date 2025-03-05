package com.example.codePicasso.domain.exchange.controller;

import com.example.codePicasso.domain.exchange.service.ExchangeRankingService;
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
    private boolean isBuy;

    @GetMapping("/buy")
    public ResponseEntity<Set<String>> getBuyTopRanking(
            @RequestParam int topN,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate endDate
            ) {
        isBuy = true;
        Set<String> topRanking = exchangeRankingService.getTopRankingInPeriod(startDate, endDate, isBuy, topN);
        return ResponseEntity.ok(topRanking);
    }

    @GetMapping("/sell")
    public ResponseEntity<Set<String>> getSellTopRanking(
            @RequestParam int topN,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate endDate
    ) {
        isBuy = false;
        Set<String> topRanking = exchangeRankingService.getTopRankingInPeriod(startDate, endDate, isBuy, topN);
        return ResponseEntity.ok(topRanking);
    }

    @GetMapping("/total/buy")
    public ResponseEntity<Set<String>> getBuyTopRankingTotal(
            @RequestParam int topN
    ) {
        isBuy = true;
        Set<String> topRanking = exchangeRankingService.getTopRanking(topN, isBuy);
        return ResponseEntity.ok(topRanking);
    }

    @GetMapping("/total/sell")
    public ResponseEntity<Set<String>> getSellTopRankingTotal() {
        isBuy = false;
        Set<String> topRanking = exchangeRankingService.getTopRanking(10, isBuy);
        return ResponseEntity.ok(topRanking);
    }

    /**
     * 특정 게임의 전체 기간 거래량 조회
     */
    @GetMapping("/total/trade-count")
    public ResponseEntity<Long> getBuyTotalTradeCount(
        @RequestParam Long gameId
        ) {
        isBuy = true;
        Long totalTradeCount = exchangeRankingService.getTotalTradeCount(gameId, isBuy);
        return ResponseEntity.ok(totalTradeCount);
    }

    @GetMapping("/trade-count/buy")
    public ResponseEntity<Map<String, Long>> getBuyTradeCountByTimePeriod(
            @RequestParam Long gameId,
            @RequestParam boolean isHourly,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        isBuy = true;
        Map<String, Long> tradeCounts = exchangeRankingService.getTradeCountByTimePeriod(gameId, startDate, endDate, isHourly, isBuy);
        return ResponseEntity.ok(tradeCounts);
    }

    @GetMapping("/trade-count/sell")
    public ResponseEntity<Map<String, Long>> getSellTradeCountByTimePeriod(
            @RequestParam Long gameId,
            @RequestParam boolean isHourly,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate
    ) {
        isBuy = false;
        Map<String, Long> tradeCounts = exchangeRankingService.getTradeCountByTimePeriod(gameId, startDate, endDate, isHourly, isBuy);
        return ResponseEntity.ok(tradeCounts);
    }
    //boolean 관련해서 리퀘스트 파람을 어떻게 처리할지. 고민 됨.

    @GetMapping("/gameId")
    public ResponseEntity<Set<String>> getGameIdByTitle(@RequestParam String gameTitle) {
        Long gameId = exchangeRankingService.getGameIdByTitle(gameTitle);
        return ResponseEntity.ok(Set.of(gameId.toString()));
    }

}
