package com.example.codePicasso.domain.exchange.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ExchangeRankingService {

    private final StringRedisTemplate redisTemplate;
    //특정 기간이 아닌 전체 기록을 체크할 때 쓰일 수 있음.
    private static final String BUY_RANKING_KEY = "ranking:buy";
    private static final String SELL_RANKING_KEY = "ranking:sell";

    private static final String DAILY_BUY_RANKING_KEY = "ranking:daily:buy";
    private static final String DAILY_SELL_RANKING_KEY = "ranking:daily:sell";
    private static final String HOURLY_BUY_RANKING_KEY = "ranking:hourly:buy";
    private static final String HOURLY_SELL_RANKING_KEY = "ranking:hourly:sell";
    private static final String GAME_ID_TITLE_HASH_KEY = "game:id:title";

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter HOUR_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");

    /**
     * 거래 완료 기준, 게임 ID의 카운트 증가 (날짜/시간 단위로 저장)
     */
    public void increaseTradeCount(Long gameId, String gameTitle, boolean isBuy) {
        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
        HashOperations<String, Object, Object> hashOps = redisTemplate.opsForHash();

        //전체 기간
        String key = isBuy ? BUY_RANKING_KEY : SELL_RANKING_KEY;
        zSetOps.incrementScore(key, gameId.toString(), 1);

        //날짜별
        String todayKey = (isBuy ? DAILY_BUY_RANKING_KEY : DAILY_SELL_RANKING_KEY) + LocalDate.now().format(DATE_FORMAT);
        zSetOps.incrementScore(todayKey, gameId.toString(), 1);

        //시간별
        String currentHourKey = (isBuy ? HOURLY_BUY_RANKING_KEY : HOURLY_SELL_RANKING_KEY) + LocalDate.now().format(HOUR_FORMAT);
        zSetOps.incrementScore(currentHourKey, gameId.toString(), 1);

        hashOps.putIfAbsent(GAME_ID_TITLE_HASH_KEY, gameId.toString(), gameTitle);
    }

    /**
     * 특정 게임의 특정 날짜 또는 시간 단위로 거래량 조회(시간별 또는 일별)
     */
    public Map<String, Long> getTradeCountByTimePeriod(Long gameId, LocalDate startDate, LocalDate endDate, boolean isHourly, boolean isBuy) {
        Map<String, Long> result = new LinkedHashMap<>();
        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();

        if (isHourly) {
            //시간별 조회
            LocalDateTime dateTime = startDate.atStartOfDay();
            LocalDateTime endDateTime = endDate.plusDays(1).atStartOfDay();
            while (dateTime.isBefore(endDateTime)) {
                String hourKey = (isBuy ? HOURLY_BUY_RANKING_KEY : HOURLY_SELL_RANKING_KEY) + dateTime.format(HOUR_FORMAT);
                Double score = zSetOps.score(hourKey, gameId.toString());
                result.put(dateTime.format(HOUR_FORMAT), score != null ? score.longValue() : 0L);
                dateTime = dateTime.plusHours(1);
            }
        } else {
            //일별 조회
            LocalDate date = startDate;
            while (date.isBefore(endDate)) {
                String todayKey = (isBuy ? DAILY_BUY_RANKING_KEY : DAILY_SELL_RANKING_KEY) + date.format(DATE_FORMAT);
                Double score = zSetOps.score(todayKey, gameId.toString());
                result.put(date.format(DATE_FORMAT), score != null ? score.longValue() : 0L);
                date = date.plusDays(1);
            }
        }
        return result;
    }

    /**
     * 전체 기간 거래량 상위 N개 게임 랭킹 조회
     */
    public Set<String> getTopRanking(int topN, boolean isBuy) {
        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
        String key = isBuy ? BUY_RANKING_KEY : SELL_RANKING_KEY;
        return zSetOps.reverseRange(key, 0, topN - 1);
    }

    /**
     * 특정 게임의 전체 기간 거래량 조회
     */
    public Long getTotalTradeCount(Long gameId, boolean isBuy) {
        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
        String key = isBuy ? BUY_RANKING_KEY : SELL_RANKING_KEY;
        Double score = zSetOps.score(key, gameId.toString());
        return (score != null) ? score.longValue() : 0L;
    }

    /**
     * 거래량 상위 N개의 데이터 조회
     */
    public Set<String> getTopRankingInPeriod(LocalDate startDate, LocalDate endDate, boolean isBuy, int topN) {
        List<String> keys = new ArrayList<>();
        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();

        //기간 내 랭킹키 모음
        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            String key = (isBuy ? DAILY_BUY_RANKING_KEY : DAILY_SELL_RANKING_KEY) + currentDate.format(DATE_FORMAT);
            keys.add(key);
            currentDate = currentDate.plusDays(1);
        }

        String tempKey = "ranking:temp" + startDate + ":" + endDate + ":" + (isBuy ? "buy" : "sell");
        if (keys.size() == 1) {
            redisTemplate.opsForZSet().unionAndStore(keys.get(0), Collections.emptyList(), tempKey);
        } else {
            redisTemplate.opsForZSet().unionAndStore(keys.get(0), keys.subList(1, keys.size()), tempKey);
        }

        Set<String> topGameIds = zSetOps.reverseRange(tempKey, 0, topN - 1);

        redisTemplate.delete(tempKey);

        return topGameIds;
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
