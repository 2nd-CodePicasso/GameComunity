package com.example.codePicasso.domain.exchange.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExchangeRankingServiceTest {

    @Mock
    private StringRedisTemplate redisTemplate;

    @Mock
    private ZSetOperations<String, String> zSetOperations;

    @InjectMocks
    private ExchangeRankingService exchangeRankingService;

    private final String BUY_RANKING_KEY = "ranking:buy";
    private final String SELL_RANKING_KEY = "ranking:sell";

    @BeforeEach
    void setUp() {
        when(redisTemplate.opsForZSet()).thenReturn(zSetOperations);
    }

    @Test
    void 거래_횟수_증가_구매() {
        // given
        String gameTitle = "GameA";
        boolean isBuy = true;

        // when
        exchangeRankingService.increaseTradeCount(gameTitle, isBuy);

        // then
        verify(zSetOperations, times(1)).incrementScore(BUY_RANKING_KEY, gameTitle, 1);
    }

    @Test
    void 거래_횟수_증가_판매() {
        // given
        String gameTitle = "GameB";
        boolean isBuy = false;

        // when
        exchangeRankingService.increaseTradeCount(gameTitle, isBuy);

        // then
        verify(zSetOperations, times(1)).incrementScore(SELL_RANKING_KEY, gameTitle, 1);
    }

    @Test
    void 상위_랭킹_조회_구매() {
        // given
        Set<String> expectedRanking = Set.of("GameA", "GameB");
        when(zSetOperations.reverseRange(BUY_RANKING_KEY, 0, 9)).thenReturn(expectedRanking);

        // when
        Set<String> result = exchangeRankingService.getTopRanking(true);

        // then
        assertThat(result).isEqualTo(expectedRanking);
        verify(zSetOperations, times(1)).reverseRange(BUY_RANKING_KEY, 0, 9);
    }

    @Test
    void 상위_랭킹_조회_판매() {
        // given
        Set<String> expectedRanking = Set.of("GameC", "GameD");
        when(zSetOperations.reverseRange(SELL_RANKING_KEY, 0, 9)).thenReturn(expectedRanking);

        // when
        Set<String> result = exchangeRankingService.getTopRanking(false);

        // then
        assertThat(result).isEqualTo(expectedRanking);
        verify(zSetOperations, times(1)).reverseRange(SELL_RANKING_KEY, 0, 9);
    }
}
