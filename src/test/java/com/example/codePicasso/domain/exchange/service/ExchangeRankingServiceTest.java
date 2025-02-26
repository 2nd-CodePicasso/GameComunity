package com.example.codePicasso.domain.exchange.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExchangeRankingServiceTest {

    @Mock
    private StringRedisTemplate redisTemplate;

    @Mock
    private ZSetOperations<String, String> zSetOperations;

    @Mock
    private HashOperations<String, Object, Object> hashOperations;

    @InjectMocks
    private ExchangeRankingService exchangeRankingService;

    private static final String BUY_RANKING_KEY = "ranking:buy";
    private static final String SELL_RANKING_KEY = "ranking:sell";
    private static final String DAILY_BUY_RANKING_PREFIX = "ranking:buy:daily:";
    private static final String DAILY_SELL_RANKING_PREFIX = "ranking:sell:daily:";
    private static final String HOURLY_BUY_RANKING_PREFIX = "ranking:buy:hourly:";
    private static final String HOURLY_SELL_RANKING_PREFIX = "ranking:sell:hourly:";
    private static final String GAME_ID_TITLE_HASH_KEY = "game:id:title";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter HOUR_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");

    @BeforeEach
    void setUp() {
        /*Redis 미사용 시 id로 title를 받아내는 과정에서 Zset 대신 Map으로 처리했으므로
        leninet를 통해 사용하지 않아도 UnnecessaryStubbingException이 뜨지 않도록 처리.*/
        lenient().when(redisTemplate.opsForZSet()).thenReturn(zSetOperations);
        when(redisTemplate.opsForHash()).thenReturn(hashOperations);
    }

    @Test
    void 거래_횟수_증가_구매() {
        // given
        Long gameId = 1L;
        String gameTitle = "GameA";
        boolean isBuy = true;

        // when
        exchangeRankingService.increaseTradeCount(gameId, gameTitle, isBuy);

        // then
        verify(zSetOperations, times(1)).incrementScore(BUY_RANKING_KEY, gameId.toString(), 1);

        String dailyKey = DAILY_BUY_RANKING_PREFIX + LocalDate.now().format(DATE_FORMAT);
        verify(zSetOperations, times(1)).incrementScore(dailyKey, gameId.toString(), 1);

        String hourlyKey = HOURLY_BUY_RANKING_PREFIX + LocalDate.now().format(HOUR_FORMAT);
        verify(zSetOperations, times(1)).incrementScore(hourlyKey, gameId.toString(), 1);

        verify(hashOperations, times(1)).putIfAbsent(GAME_ID_TITLE_HASH_KEY, gameId.toString(), gameTitle);
    }

    @Test
    void 거래_횟수_증가_판매() {
        // given
        Long gameId = 2L;
        String gameTitle = "GameB";
        boolean isBuy = false;

        // when
        exchangeRankingService.increaseTradeCount(gameId, gameTitle, isBuy);

        // then
        verify(zSetOperations, times(1)).incrementScore(SELL_RANKING_KEY, gameId.toString(), 1);

        String dailyKey = DAILY_SELL_RANKING_PREFIX + LocalDate.now().format(DATE_FORMAT);
        verify(zSetOperations, times(1)).incrementScore(dailyKey, gameId.toString(), 1);

        String hourlyKey = HOURLY_SELL_RANKING_PREFIX + LocalDate.now().format(HOUR_FORMAT);
        verify(zSetOperations, times(1)).incrementScore(hourlyKey, gameId.toString(), 1);

        verify(hashOperations, times(1)).putIfAbsent(GAME_ID_TITLE_HASH_KEY, gameId.toString(), gameTitle);
    }

    @Test
    void 전체_기간_상위_랭킹_조회_구매() {
        // given
        Set<String> topGameIds = Set.of("1", "2");
        when(zSetOperations.reverseRange(BUY_RANKING_KEY, 0, 9)).thenReturn(topGameIds);
        when(hashOperations.get(GAME_ID_TITLE_HASH_KEY, "1")).thenReturn("GameA");
        when(hashOperations.get(GAME_ID_TITLE_HASH_KEY, "2")).thenReturn("GameB");

        // when
        Set<String> result = exchangeRankingService.getTopRanking(10, true);

        // then
        assertThat(result).containsExactlyInAnyOrder("GameA", "GameB");
        verify(zSetOperations, times(1)).reverseRange(BUY_RANKING_KEY, 0, 9);
    }

    @Test
    void 상위_랭킹_조회_판매() {
        // given
        Set<String> topGameIds = Set.of("3", "4");
        when(zSetOperations.reverseRange(SELL_RANKING_KEY, 0, 9)).thenReturn(topGameIds);
        when(hashOperations.get(GAME_ID_TITLE_HASH_KEY, "3")).thenReturn("GameC");
        when(hashOperations.get(GAME_ID_TITLE_HASH_KEY, "4")).thenReturn("GameD");

        // when
        Set<String> result = exchangeRankingService.getTopRanking(10, false);

        // then
        assertThat(result).containsExactlyInAnyOrder("GameC", "GameD");
        verify(zSetOperations, times(1)).reverseRange(SELL_RANKING_KEY, 0, 9);
    }

    @Test
    void 게임_타이틀로_gameId_조회() {
        //given
        String gameTitle = "GameA";
        Long gameId = 1L;

        //Redis 해시 맵 엔트리 시뮬레이션을 위한 임시 데이터
        Map<Object, Object> mockGameIdTitleMap = new HashMap<>();
        mockGameIdTitleMap.put("1", "GameA");
        mockGameIdTitleMap.put("2", "GameB");

        //HashOperations의 entries 메서드에서 임시 데이터 반환
        when(hashOperations.entries(GAME_ID_TITLE_HASH_KEY)).thenReturn(mockGameIdTitleMap);

        //when
        Long actualGameId = exchangeRankingService.getGameIdByTitle(gameTitle);
        //then
        //gameId 일치 확인
        assertThat(actualGameId).isEqualTo(gameId);
        //HashOperations의 entries 호출 검증
        verify(hashOperations, times(1)).entries(GAME_ID_TITLE_HASH_KEY);
    }
}
