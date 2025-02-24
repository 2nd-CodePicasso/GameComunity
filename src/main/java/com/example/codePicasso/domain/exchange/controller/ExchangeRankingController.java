package com.example.codePicasso.domain.exchange.controller;

import com.example.codePicasso.domain.exchange.service.ExchangeRankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exchanges")
public class ExchangeRankingController {

    private final ExchangeRankingService exchangeRankingService;
    private boolean isBuy;

    @GetMapping
    public ResponseEntity<Set<String>> getBuyTopRanking() {
        isBuy = true;
        Set<String> topRanking = exchangeRankingService.getTopRanking(isBuy);
        return ResponseEntity.ok(topRanking);
    }

    @GetMapping
    public ResponseEntity<Set<String>> getSellTopRanking() {
        isBuy = false;
        Set<String> topRanking = exchangeRankingService.getTopRanking(isBuy);
        return ResponseEntity.ok(topRanking);
    }

}
