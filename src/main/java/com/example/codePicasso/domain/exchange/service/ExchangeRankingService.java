package com.example.codePicasso.domain.exchange.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ExchangeRankingService {

    private final StringRedisTemplate redisTemplate;
    private final String BUY_RANKING_KEY = "ranking:buy";
    private final String SELL_RANKING_KEY = "ranking:sell";

    /**
     * 거래 완료 기준, 게임 ID의 카운트 증가
     */
    public void increaseTradeCount(String gameTitle, boolean isBuy) {
        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
        String key = isBuy ? BUY_RANKING_KEY : SELL_RANKING_KEY;
        zSetOps.incrementScore(key, gameTitle, 1);
    }

    /**
     * 거래량 상위 10개의 데이터 조회
     */
    public Set<String> getTopRanking(boolean isBuy) {
        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
        String key = isBuy ? BUY_RANKING_KEY : SELL_RANKING_KEY;
        return zSetOps.reverseRange(key, 0, 9);
    }
}
