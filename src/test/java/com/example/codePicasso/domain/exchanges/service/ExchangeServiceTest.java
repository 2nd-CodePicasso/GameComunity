package com.example.codePicasso.domain.exchanges.service;

import com.example.codePicasso.domain.exchange.dto.request.ExchangeRequest;
import com.example.codePicasso.domain.exchange.dto.response.ExchangeResponse;
import com.example.codePicasso.domain.exchange.entity.Exchange;
import com.example.codePicasso.domain.exchange.entity.TradeType;
import com.example.codePicasso.domain.exchange.redis.RedisLockService;
import com.example.codePicasso.domain.exchange.service.ExchangeConnector;
import com.example.codePicasso.domain.exchange.service.ExchangeRankingService;
import com.example.codePicasso.domain.exchange.service.ExchangeService;
import com.example.codePicasso.domain.game.entity.Game;
import com.example.codePicasso.domain.game.service.GameConnector;
import com.example.codePicasso.domain.users.entity.User;
import com.example.codePicasso.domain.users.service.UserConnector;
import com.example.codePicasso.global.exception.base.NotFoundException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ExchangeServiceTest {

    @Mock
    private ExchangeConnector exchangeConnector;

    @Mock
    private GameConnector gameConnector;

    @Mock
    private UserConnector userConnector;

    @Mock
    private RedisLockService redisLockService;

    @Mock
    private ExchangeRankingService exchangeRankingService;

    @InjectMocks
    private ExchangeService exchangeService;

    private ExchangeRequest exchangeRequest;
    private Exchange exchange;
    private Page<Exchange> exchanges;
    private User user;
    private Game game;
    private Long exchangeId = 1L;
    private Long userId = 1L;
    private Long gameId = 1L;
    private int page = 0;
    private int size = 10;
    private Pageable pageable = PageRequest.of(page, size);

    @BeforeEach
    void 설정() {
        // spy 는 실제 작동하는 실제 객체 만듦
        // mock 은 작동하지 않는 가짜 객체 만듦
        user = mock(User.class);
        game = mock(Game.class);
        exchangeRequest = new ExchangeRequest(1L, "거래소", 100, "거래소", 100, "010-1234-5678");
        exchange = exchangeRequest.toEntity(user, game, TradeType.BUY);
        exchanges = new PageImpl<>(List.of(exchange));

        when(userConnector.findById(userId)).thenReturn(user);
        when(gameConnector.findById(gameId)).thenReturn(game);
        when(exchangeConnector.findById(exchangeId)).thenReturn(exchange);

        when(user.getId()).thenReturn(userId);
        when(game.getId()).thenReturn(userId);
    }

    @Test
    void 거래소_게시글_만들기() {
        // given
        when(exchangeConnector.save(any(Exchange.class))).thenReturn(exchange);

        // when
        ExchangeResponse response = exchangeService.createExchange(exchangeRequest, TradeType.BUY, userId);

        // then
        ExchangeResponse expectedResponse = convertToResponse(exchange, userId);
        Assertions.assertThat(response)
            .usingRecursiveComparison()
            .ignoringFields("id") // id는 자동 생성되므로 비교에서 제외
            .isEqualTo(expectedResponse);
    }

    @Test
    void 거래소_게시글_가져오기_구매_Param_gameId_Yes() {
        // given
        when(exchangeConnector.findByGameIdAndTradeType(gameId, TradeType.BUY, pageable)).thenReturn(exchanges);

        // when
        Page<ExchangeResponse> responses = exchangeService.getBuyExchanges(gameId, page, size);

        // then
        verify(exchangeConnector).findByGameIdAndTradeType(gameId, TradeType.BUY, pageable);
        assertNotNull(responses);
        assertEquals(1, responses.getSize());
    }

    @Test
    void 거래소_게시글_가져오기_판매_Param_gameId_Yes() {
        // given
        when(exchangeConnector.findByGameIdAndTradeType(gameId, TradeType.SELL, pageable)).thenReturn(exchanges);

        // when
        Page<ExchangeResponse> responses = exchangeService.getSellExchanges(gameId, page, size);

        // then
        verify(exchangeConnector).findByGameIdAndTradeType(gameId, TradeType.SELL, pageable);
        assertNotNull(responses);
        assertEquals(1, responses.getSize());
    }

    @Test
    void 거래소_게시글_가져오기_특정() {
        // given
        when(exchangeConnector.findById(exchangeId)).thenReturn(exchange);

        // when
        ExchangeResponse response = exchangeService.getExchangeById(exchangeId);

        // then
        verify(exchangeConnector).findById(exchangeId);
        assertNotNull(response);
    }

    @Test
    void 거래소_게시글_수정하기() {
        // given
        ExchangeRequest newReq = new ExchangeRequest(1L, "짱거래소", 1000, "거래소", 100, "010-1234-5678");
        when(exchangeConnector.save(any(Exchange.class))).thenReturn(exchange);
        when(exchange.getUser().getId()).thenReturn(userId);

        // when
        ExchangeResponse response = exchangeService.updateExchange(exchangeId, newReq, userId);

        // then
        ExchangeResponse expectedResponse = new ExchangeResponse(
            exchange.getId(),
            userId,
            exchange.getGame().getId(),
            "짱거래소", // 변경된 값
            1000, // 변경된 값
            exchange.getDescription(),
            exchange.getQuantity(),
            exchange.getContact(),
            exchange.getTradeType(),
            exchange.getStatusType()
        );

        Assertions.assertThat(response)
            .usingRecursiveComparison()
            .ignoringFields("id") // id는 자동 생성되므로 비교에서 제외
            .isEqualTo(expectedResponse);
    }

    @Test
    void 거래소_게시글_삭제하기() {
        // given
        when(exchange.getUser().getId()).thenReturn(userId);

        // when
        exchangeService.deleteExchange(exchangeId, userId);

        // then
        verify(exchangeConnector, atLeastOnce()).deleteById(exchangeId);
    }

    // 거래 완료 시 Redisson 락이 정상적으로 획득 및 해제.
    @Test
    void 거래_완료시_Redis_락_획득과_해제() {
        // given
        when(redisLockService.acquireLock(exchangeId)).thenReturn(true); // 락 획득 성공
        when(exchangeConnector.findById(exchangeId)).thenReturn(exchange);
        doNothing().when(exchangeRankingService).increaseTradeCount(anyLong(), anyBoolean()); // 거래 랭킹 업데이트 목 처리

        // when
        exchangeService.completeExchange(exchangeId);

        // then
        verify(redisLockService).acquireLock(exchangeId); // 락 획득 검증
        verify(exchangeRankingService).increaseTradeCount(exchange.getGame().getId(), exchange.getTradeType() == TradeType.BUY); // 거래 랭킹 업데이트 검증
        verify(exchangeConnector).save(exchange); // 거래 상태 저장 검증
        verify(redisLockService).releaseLock(exchangeId); // 락 해제 검증
    }

    // 거래 완료 시 Redisson 락을 얻지 못한 경우 예외 발생 여부
    @Test
    void 거래_완료시_Redis_락_획득_실패시_예외발생() {
        // given
        when(redisLockService.acquireLock(exchangeId)).thenReturn(false); // 락 획득 실패

        // when & then
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            exchangeService.completeExchange(exchangeId);
        });

        assertEquals("거래 완료 처리가 이미 진행 중입니다.", exception.getMessage());
        verify(redisLockService, never()).releaseLock(exchangeId); // 락 해제 호출되지 않음 검증
    }

    // 예외 or 다른 로직 처리
    @Test
    void 거래소_게시글_가져오기_구매_Param_gameId_No() {
        // given
        when(exchangeConnector.findByTradeType(TradeType.BUY, pageable)).thenReturn(exchanges);

        // when
        Page<ExchangeResponse> responses = exchangeService.getBuyExchanges(null, page, size);

        // then
        verify(exchangeConnector).findByTradeType(TradeType.BUY, pageable);
        assertNotNull(responses);
        assertEquals(1, responses.getSize());
    }

    @Test
    void 거래소_게시글_USER_NOT_FOUND() {
        // given
        Long wrongUserId = 2L;
        when(exchange.getUser().getId()).thenReturn(userId);

        // when & then
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            exchangeService.deleteExchange(exchangeId, wrongUserId);
        });
        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void 거래소_게시글_EXCHANGE_NOT_FOUND() {
        // given
        Long wrongExchangeId = 2L;
        when(exchangeConnector.findById(wrongExchangeId)).thenThrow(new NotFoundException(ErrorCode.EXCHANGE_NOT_FOUND));

        // when & then
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            exchangeService.deleteExchange(wrongExchangeId, userId);
        });
        assertEquals(ErrorCode.EXCHANGE_NOT_FOUND, exception.getErrorCode());
    }

    private ExchangeResponse convertToResponse(Exchange exchange, Long userId) {
        return ExchangeResponse.builder()
            .id(exchange.getId())
            .userId(userId)
            .gameId(exchange.getGame().getId())
            .title(exchange.getTitle())
            .price(exchange.getPrice())
            .description(exchange.getDescription())
            .quantity(exchange.getQuantity())
            .contact(exchange.getContact())
            .tradeType(exchange.getTradeType())
            .statustype(exchange.getStatusType())
            .build();
    }
}

