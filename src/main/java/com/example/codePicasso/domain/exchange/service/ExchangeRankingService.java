package com.example.codePicasso.domain.exchange.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExchangeRankingService {

    private final StringRedisTemplate redisTemplate;
    private static final String BUY_RANKING_KEY = "ranking:buy";
    private static final String SELL_RANKING_KEY = "ranking:sell";
    private static final String GAME_ID_TITLE_HASH_KEY = "game:id:title";

    /**
     * 거래 완료 기준, 게임 ID의 카운트 증가
     */
    public void increaseTradeCount(Long gameId, String gameTitle, boolean isBuy) {
        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
        HashOperations<String, Object, Object> hashOps = redisTemplate.opsForHash();

        String key = isBuy ? BUY_RANKING_KEY : SELL_RANKING_KEY;
        zSetOps.incrementScore(key, gameId.toString(), 1);
        hashOps.putIfAbsent(GAME_ID_TITLE_HASH_KEY, gameId.toString(), gameTitle);
    }

    /**
     * 거래량 상위 10개의 데이터 조회
     */
    public Set<String> getTopRanking(boolean isBuy, int topN) {
        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
        HashOperations<String, Object, Object> hashOps = redisTemplate.opsForHash();
        String key = isBuy ? BUY_RANKING_KEY : SELL_RANKING_KEY;

        Set<String> topGameIds = zSetOps.reverseRange(key, 0, topN - 1);

        return topGameIds.stream()
                .map(gameId -> (String) hashOps.get(GAME_ID_TITLE_HASH_KEY, gameId))
                .collect(Collectors.toSet());
    }

    /**
     *  게임 타이틀을 클릭하면 해당 gameId 조회
     */
    public Long getGameIdByTitle(String gameTitle) {
        HashOperations<String, Object, Object> hashOps = redisTemplate.opsForHash();

        Map<Object, Object> entries = hashOps.entries(GAME_ID_TITLE_HASH_KEY);

        return entries.entrySet().stream()
                .filter(entry -> gameTitle.equals(entry.getValue()))
                .map(entry -> Long.valueOf((String) entry.getKey()))
                .findFirst()
                .orElse(null);
    }

}
