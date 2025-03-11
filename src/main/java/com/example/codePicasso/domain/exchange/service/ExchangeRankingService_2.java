//package com.example.codePicasso.domain.exchange.service;
//
//import java.util.stream.Collectors;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.redis.core.HashOperations;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.core.ZSetOperations;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.*;
//
//@Service
//@RequiredArgsConstructor
//public class ExchangeRankingService_2 {
//
//    private final StringRedisTemplate redisTemplate;
//    //특정 기간이 아닌 전체 기록을 체크할 때 쓰일 수 있음.
//    private static final String BUY_RANKING_KEY = "ranking:buy";
//    private static final String SELL_RANKING_KEY = "ranking:sell";
//
//    private static final String DAILY_BUY_RANKING_KEY = "ranking:buy:daily:";
//    private static final String DAILY_SELL_RANKING_KEY = "ranking:sell:daily:";
//    private static final String HOURLY_BUY_RANKING_KEY = "ranking:buy:hourly:";
//    private static final String HOURLY_SELL_RANKING_KEY = "ranking:sell:hourly:";
//
//    private static final String GAME_ID_TITLE_HASH_KEY = "game:id:title";
//    private static final String GAME_TITLE_ID_HASH_KEY = "game:title:id";
//
//    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//    private static final DateTimeFormatter HOUR_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
//
//    /**
//     * 거래 완료 기준, 게임 ID의 카운트 증가 (날짜/시간 단위로 저장)
//     */
//
//    protected double getCurrentTimestamp() {
//        return System.currentTimeMillis() / 1000.0;
//    }
//
//    public void increaseTradeCount(Long gameId, String gameTitle, boolean isBuy) {
//        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
//        HashOperations<String, Object, Object> hashOps = redisTemplate.opsForHash();
//
//        // 현재 시간 기준 타임스탬프를 가져옴 (테스트에서는 Mocking 가능)
//        double timestamp = getCurrentTimestamp();
//        double adjustedScore = 1 + (timestamp / 1_000_000.0);
//
//        // 전체 기간 랭킹 증가
//        String key = isBuy ? BUY_RANKING_KEY : SELL_RANKING_KEY;
//        zSetOps.incrementScore(key, gameId.toString(), adjustedScore);
//
//        // 일별 랭킹 증가
//        String todayKey = (isBuy ? DAILY_BUY_RANKING_KEY : DAILY_SELL_RANKING_KEY) + LocalDate.now().format(DATE_FORMAT);
//        zSetOps.incrementScore(todayKey, gameId.toString(), adjustedScore);
//
//        // 시간별 랭킹 증가
//        String currentHourKey = (isBuy ? HOURLY_BUY_RANKING_KEY : HOURLY_SELL_RANKING_KEY) + LocalDateTime.now().format(HOUR_FORMAT);
//        zSetOps.incrementScore(currentHourKey, gameId.toString(), adjustedScore);
//
//        // 게임 ID ↔ 타이틀 매핑 저장
//        hashOps.putIfAbsent(GAME_ID_TITLE_HASH_KEY, gameId.toString(), gameTitle);
//        hashOps.putIfAbsent(GAME_TITLE_ID_HASH_KEY, gameTitle, gameId.toString());
//    }
//
//    /**
//     * 특정 게임의 특정 날짜 또는 시간 단위로 거래량 조회(시간별 또는 일별)
//     */
//    public Map<String, Long> getTradeCountByTimePeriod(Long gameId, LocalDate startDate, LocalDate endDate, boolean isHourly, boolean isBuy) {
//        Map<String, Long> result = new LinkedHashMap<>();
//        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
//
//        if (isHourly) {
//            LocalDateTime dateTime = startDate.atStartOfDay();
//            LocalDateTime endDateTime = endDate.plusDays(1).atStartOfDay();
//            while (dateTime.isBefore(endDateTime)) {
//                String hourKey = (isBuy ? HOURLY_BUY_RANKING_KEY : HOURLY_SELL_RANKING_KEY) + dateTime.format(HOUR_FORMAT);
//                Double score = zSetOps.score(hourKey, gameId.toString());
//                result.put(dateTime.format(HOUR_FORMAT), (score != null) ? score.longValue() : 0L);
//                dateTime = dateTime.plusHours(1);
//            }
//        } else {
//            LocalDate date = startDate;
//            while (!date.isAfter(endDate)) {
//                String todayKey = (isBuy ? DAILY_BUY_RANKING_KEY : DAILY_SELL_RANKING_KEY) + date.format(DATE_FORMAT);
//                Double score = zSetOps.score(todayKey, gameId.toString());
//                result.put(date.format(DATE_FORMAT), (score != null) ? score.longValue() : 0L);
//                date = date.plusDays(1);
//            }
//        }
//        return result;
//    }
//
//    /**
//     * 전체 기간 거래량 상위 N개 게임 랭킹 조회
//     */
//    public Set<String> getTopRanking(int topN, boolean isBuy) {
//        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
//        HashOperations<String, Object, Object> hashOps = redisTemplate.opsForHash();
//        String key = isBuy ? BUY_RANKING_KEY : SELL_RANKING_KEY;
//
//        Set<String> topGameIds = zSetOps.reverseRange(key, 0, topN - 1);
//        if (topGameIds == null) return Set.of();
//
//        return topGameIds.stream()
//            .map(gameId -> (String) hashOps.get(GAME_ID_TITLE_HASH_KEY, gameId))
//            .filter(Objects::nonNull)
//            .collect(Collectors.toSet());
//    }
//
//    /**
//     * 특정 게임의 전체 기간 거래량 조회
//     */
//    public Long getTotalTradeCount(Long gameId, boolean isBuy) {
//        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
//        String key = isBuy ? BUY_RANKING_KEY : SELL_RANKING_KEY;
//        Double score = zSetOps.score(key, gameId.toString());
//        return (score != null) ? score.longValue() : 0L;
//    }
//
//    /**
//     * 거래량 상위 N개의 데이터 조회
//     */
//    public Set<String> getTopRankingInPeriod(LocalDate startDate, LocalDate endDate, boolean isBuy, int topN) {
//        List<String> keys = new ArrayList<>();
//        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
//
//        //기간 내 랭킹키 모음
//        LocalDate currentDate = startDate;
//        while (!currentDate.isAfter(endDate)) {
//            String key = (isBuy ? DAILY_BUY_RANKING_KEY : DAILY_SELL_RANKING_KEY) + currentDate.format(DATE_FORMAT);
//            keys.add(key);
//            currentDate = currentDate.plusDays(1);
//        }
//
//        String tempKey = "ranking:temp" + startDate + ":" + endDate + ":" + (isBuy ? "buy" : "sell");
//        if (keys.size() == 1) {
//            redisTemplate.opsForZSet().unionAndStore(keys.get(0), Collections.emptyList(), tempKey);
//        } else {
//            redisTemplate.opsForZSet().unionAndStore(keys.get(0), keys.subList(1, keys.size()), tempKey);
//        }
//
//        Set<String> topGameIds = zSetOps.reverseRange(tempKey, 0, topN - 1);
//
//        redisTemplate.delete(tempKey);
//
//        return topGameIds;
//    }
//
//    /**
//     *  게임 타이틀을 클릭하면 해당 gameId 조회
//     */
//    public Long getGameIdByTitle(String gameTitle) {
//        HashOperations<String, Object, Object> hashOps = redisTemplate.opsForHash();
//        String gameId = (String) hashOps.get(GAME_TITLE_ID_HASH_KEY, gameTitle);
//        return (gameId != null) ? Long.valueOf(gameId) : null;
//    }
//
//}
