package com.example.codePicasso.domain.exchange.service;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
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
    @Spy
    private ExchangeRankingService exchangeRankingService;

    private static final String BUY_RANKING_KEY = "ranking:buy";
    private static final String SELL_RANKING_KEY = "ranking:sell";
    private static final String DAILY_BUY_RANKING_PREFIX = "ranking:buy:daily:";
    private static final String DAILY_SELL_RANKING_PREFIX = "ranking:sell:daily:";
    private static final String HOURLY_BUY_RANKING_PREFIX = "ranking:buy:hourly:";
    private static final String HOURLY_SELL_RANKING_PREFIX = "ranking:sell:hourly:";
    private static final String GAME_ID_TITLE_HASH_KEY = "game:id:title";
    private static final String GAME_TITLE_ID_HASH_KEY = "game:title:id";

    private final double MOCK_TIMESTAMP = 1740575.883893;

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
        double adjustedScore = 1 + (MOCK_TIMESTAMP / 1_000_000.0);  // 예상 점수

        // Mocking된 타임스탬프 값을 올바르게 설정
        doReturn(MOCK_TIMESTAMP).when(exchangeRankingService).getCurrentTimestamp();

        // when
        exchangeRankingService.increaseTradeCount(gameId, gameTitle, isBuy);

        // then - 랭킹 데이터 증가 검증
        verify(zSetOperations, times(1)).incrementScore(BUY_RANKING_KEY, gameId.toString(), adjustedScore);

        String dailyKey = DAILY_BUY_RANKING_PREFIX + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        verify(zSetOperations, times(1)).incrementScore(dailyKey, gameId.toString(), adjustedScore);

        String hourlyKey = HOURLY_BUY_RANKING_PREFIX + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH"));
        verify(zSetOperations, times(1)).incrementScore(hourlyKey, gameId.toString(), adjustedScore);

        // then - 게임 ID, 타이틀 저장 검증
        verify(hashOperations, times(1)).putIfAbsent(GAME_ID_TITLE_HASH_KEY, gameId.toString(), gameTitle);
        verify(hashOperations, times(1)).putIfAbsent(GAME_TITLE_ID_HASH_KEY, gameTitle, gameId.toString());
    }

    @Test
    void 거래_횟수_증가_판매() {
        // given
        Long gameId = 2L;
        String gameTitle = "GameB";
        boolean isBuy = false;
        double adjustedScore = 1 + (MOCK_TIMESTAMP / 1_000_000.0);

        // Mocking된 타임스탬프 값을 올바르게 설정
        doReturn(MOCK_TIMESTAMP).when(exchangeRankingService).getCurrentTimestamp();

        // when
        exchangeRankingService.increaseTradeCount(gameId, gameTitle, isBuy);

        // then - 랭킹 데이터 증가 검증
        verify(zSetOperations, times(1)).incrementScore(SELL_RANKING_KEY, gameId.toString(), adjustedScore);

        String dailyKey = DAILY_SELL_RANKING_PREFIX + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        verify(zSetOperations, times(1)).incrementScore(dailyKey, gameId.toString(), adjustedScore);

        String hourlyKey = HOURLY_SELL_RANKING_PREFIX + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH"));
        verify(zSetOperations, times(1)).incrementScore(hourlyKey, gameId.toString(), adjustedScore);

        // then - 게임 ID ↔ 타이틀 저장 검증
        verify(hashOperations, times(1)).putIfAbsent(GAME_ID_TITLE_HASH_KEY, gameId.toString(), gameTitle);
        verify(hashOperations, times(1)).putIfAbsent(GAME_TITLE_ID_HASH_KEY, gameTitle, gameId.toString());
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

        // then - gameTitle이 올바르게 매핑되었는지 검증
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

        // then - gameTitle이 올바르게 매핑되었는지 검증
        assertThat(result).containsExactlyInAnyOrder("GameC", "GameD");
        verify(zSetOperations, times(1)).reverseRange(SELL_RANKING_KEY, 0, 9);
    }

    @Test
    void 게임_타이틀로_gameId_조회() {
        // given
        String gameTitle = "GameA";
        Long expectedGameId = 1L;

        // HashOperations에서 바로 gameTitle을 Key로 gameId를 찾도록 개선
        when(hashOperations.get(GAME_TITLE_ID_HASH_KEY, gameTitle)).thenReturn(expectedGameId.toString());

        // when
        Long actualGameId = exchangeRankingService.getGameIdByTitle(gameTitle);

        // then - O(1) 조회가 정상적으로 작동하는지 검증
        assertThat(actualGameId).isEqualTo(expectedGameId);
        verify(hashOperations, times(1)).get(GAME_TITLE_ID_HASH_KEY, gameTitle);
    }
}
