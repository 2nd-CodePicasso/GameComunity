package com.example.codePicasso.domain.exchange.controller;

import com.example.codePicasso.domain.exchange.service.ExchangeRankingService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExchangeRankingController {

    private final ExchangeRankingService exchangeRankingService;

    @GetMapping
    public ResponseEntity<Set<String>> getTopRanking(@RequestParam boolean isBuy) {
        Set<String> topRanking = exchangeRankingService.getTopRanking(isBuy);
        return ResponseEntity.ok(topRanking);
    }

}
