package com.example.codePicasso.domain.exchange.service;

import com.example.codePicasso.domain.exchange.dto.request.ExchangeRequest;
import com.example.codePicasso.domain.exchange.dto.request.MyExchangeRequest;
import com.example.codePicasso.domain.exchange.dto.request.PutExchangeRequest;
import com.example.codePicasso.domain.exchange.dto.response.ExchangeResponse;
import com.example.codePicasso.domain.exchange.dto.response.MyExchangeResponse;
import com.example.codePicasso.domain.exchange.entity.Exchange;
import com.example.codePicasso.domain.exchange.entity.MyExchange;
import com.example.codePicasso.domain.exchange.entity.StatusType;
import com.example.codePicasso.domain.exchange.entity.TradeType;
import com.example.codePicasso.domain.exchange.redis.RedisLockService;
import com.example.codePicasso.domain.game.entity.Game;
import com.example.codePicasso.domain.game.service.GameConnector;
import com.example.codePicasso.domain.user.entity.User;
import com.example.codePicasso.domain.user.service.UserConnector;
import com.example.codePicasso.global.common.DtoFactory;
import com.example.codePicasso.global.exception.base.DuplicateException;
import com.example.codePicasso.global.exception.base.InvalidRequestException;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ExchangeServiceTest {
    @Mock
    private ExchangeConnector exchangeConnector;

    @Mock
    private MyExchangeConnector myExchangeConnector;

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

    /// --- Exchange ---
    private ExchangeRequest exchangeRequest;
    private ExchangeResponse exchangeResponse;
    private Exchange exchange;
    private Page<Exchange> exchanges;

    /// --- MyExchange ---
    private MyExchangeRequest myExchangeRequest;
    private MyExchangeResponse myExchangeResponse;
    private MyExchange myExchange;
    private Page<MyExchange> myExchanges;
    private PutExchangeRequest putCanceledMyExchangeRequest;

    /// --- Entity ---
    private User user;
    private Game game;

    /// --- Id ---
    private Long exchangeId = 1L;
    private Long myExchangeId = 1L;
    private Long userId = 1L;
    private Long gameId = 1L;

    /// --- Page ---
    private int page = 0;
    private int size = 10;
    private Pageable pageable = PageRequest.of(page, size);

    @BeforeEach
    void 설정() {
        user = mock(User.class);
        game = mock(Game.class);

        exchangeRequest = new ExchangeRequest(1L, "거래소", 100, "거래소", 100, "010-1234-5678");
        exchange = exchangeRequest.toEntity(user, game, TradeType.BUY);
        exchanges = new PageImpl<>(List.of(exchange));
        exchangeResponse = DtoFactory.toExchangeDto(exchange);

        myExchangeRequest = new MyExchangeRequest("01012341234");
        myExchange = myExchangeRequest.toEntity(exchange, user);
        myExchanges = new PageImpl<>(List.of(myExchange));
        myExchangeResponse = DtoFactory.toMyExchangeDto(myExchange);
        putCanceledMyExchangeRequest = new PutExchangeRequest(StatusType.CANCELED);

        when(userConnector.findById(userId)).thenReturn(user);
        when(gameConnector.findByIdForUser(gameId)).thenReturn(game);
        when(exchangeConnector.findById(exchangeId)).thenReturn(exchange);

        when(user.getId()).thenReturn(userId);
        when(game.getId()).thenReturn(gameId);
    }

    /// --- Exchange ✅ ---
    @Test
    void 거래소_아이템_생성() {
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
    void 거래소_아이템_조회_구매_Param_gameId_Yes() {
        // given
        when(exchangeConnector.findByGameIdAndTradeType(gameId, TradeType.BUY, pageable)).thenReturn(exchanges);

        // when
        Page<ExchangeResponse> responses = exchangeService.getExchanges(TradeType.BUY, gameId, page, size);

        // then
        verify(exchangeConnector).findByGameIdAndTradeType(gameId, TradeType.BUY, pageable);
        assertNotNull(responses);
        assertEquals(1, responses.getSize());
    }

    @Test
    void 거래소_아이템_조회_판매_Param_gameId_Yes() {
        // given
        when(exchangeConnector.findByGameIdAndTradeType(gameId, TradeType.SELL, pageable)).thenReturn(exchanges);

        // when
        Page<ExchangeResponse> responses = exchangeService.getExchanges(TradeType.SELL, gameId, page, size);

        // then
        verify(exchangeConnector).findByGameIdAndTradeType(gameId, TradeType.SELL, pageable);
        assertNotNull(responses);
        assertEquals(1, responses.getSize());
    }

    @Test
    void 거래소_아이템_조회_특정_아이템() {
        // given
        when(exchangeConnector.findById(exchangeId)).thenReturn(exchange);

        // when
        ExchangeResponse response = exchangeService.getExchangeById(exchangeId);

        // then
        verify(exchangeConnector).findById(exchangeId);
        assertNotNull(response);
    }

    @Test
    void 거래소_아이템_수정() {
        // given
        ExchangeRequest newReq = new ExchangeRequest(1L, "짱거래소", 1000, "거래소", 100, "010-1234-5678");
        when(exchangeConnector.findById(exchangeId)).thenReturn(exchange);
        when(exchangeConnector.save(any(Exchange.class))).thenReturn(exchange);

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
    void 거래소_아이템_삭제() {
        // given
        when(exchange.getUser().getId()).thenReturn(userId);

        // when
        exchangeService.deleteExchange(exchangeId, userId);

        // then
        verify(exchangeConnector).deleteById(exchangeId);
    }
    /// --- Exchange ✅ ---

    /// --- Exchange ❌ ---
    @Test
    void 거래소_아이템_생성_게임_없음() {
        // given
        Long wrongGameId = 999L;
        when(gameConnector.findByIdForUser(wrongGameId)).thenThrow(new NotFoundException(ErrorCode.GAME_NOT_FOUND));

        ExchangeRequest requestWithWrongGameId = new ExchangeRequest(wrongGameId, "거래소", 100, "거래소", 100, "010-1234-5678");

        // when & then
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            exchangeService.createExchange(requestWithWrongGameId, TradeType.BUY, userId);
        });

        assertEquals(ErrorCode.GAME_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void 거래소_아이템_조회_구매_Param_gameId_No() {
        // given
        when(exchangeConnector.findByTradeType(TradeType.BUY, pageable)).thenReturn(exchanges);

        // when
        Page<ExchangeResponse> responses = exchangeService.getExchanges(TradeType.BUY, null, page, size);

        // then
        verify(exchangeConnector).findByTradeType(TradeType.BUY, pageable);
        assertNotNull(responses);
        assertEquals(1, responses.getSize());
    }

    @Test
    void 거래소_아이템_생성_유저_없음() {
        // given
        Long wrongUserId = 999L;
        when(userConnector.findById(wrongUserId)).thenThrow(new NotFoundException(ErrorCode.USER_NOT_FOUND));

        // when & then
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            exchangeService.createExchange(exchangeRequest, TradeType.BUY, wrongUserId);
        });

        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void 내_구매하기_목록_빈결과() {
        // given
        Page<MyExchange> emptyPage = new PageImpl<>(List.of());
        when(myExchangeConnector.findByUserIdAndTradeType(userId, TradeType.BUY, pageable)).thenReturn(emptyPage);

        // when
        Page<MyExchangeResponse> responses = exchangeService.getAllMyExchange(TradeType.BUY, userId, page, size);

        // then
        assertNotNull(responses);
        assertEquals(0, responses.getTotalElements());
    }

    @Test
    void 내_판매하기_목록_빈결과() {
        // given
        Page<MyExchange> emptyPage = new PageImpl<>(List.of());
        when(myExchangeConnector.findByUserIdAndTradeType(userId, TradeType.SELL, pageable)).thenReturn(emptyPage);

        // when
        Page<MyExchangeResponse> responses = exchangeService.getAllMyExchange(TradeType.SELL, userId, page, size);

        // then
        assertNotNull(responses);
        assertEquals(0, responses.getTotalElements());
    }

    @Test
    void 거래소_아이템_수정_권한없음() {
        // given
        Long anotherUserId = 999L;
        when(exchangeConnector.findById(exchangeId)).thenReturn(exchange);
        when(exchange.getUser().getId()).thenReturn(userId);

        ExchangeRequest newReq = new ExchangeRequest(1L, "업데이트된 거래소", 500, "업데이트", 200, "010-5678-1234");

        // when & then
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            exchangeService.updateExchange(exchangeId, newReq, anotherUserId);
        });

        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void 거래소_아이템_USER_NOT_FOUND() {
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
    void 거래소_아이템_EXCHANGE_NOT_FOUND() {
        // given
        Long wrongExchangeId = 2L;
        when(exchangeConnector.findById(wrongExchangeId)).thenThrow(new NotFoundException(ErrorCode.EXCHANGE_NOT_FOUND));

        // when & then
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            exchangeService.deleteExchange(wrongExchangeId, userId);
        });
        assertEquals(ErrorCode.EXCHANGE_NOT_FOUND, exception.getErrorCode());
    }
    /// --- Exchange ❌ ---

    /// --- MyExchange ✅ ---
    @Test
    void 판매하기_구매하기() {
        // given
        when(exchangeConnector.save(any(Exchange.class))).thenReturn(exchange);
        when(myExchangeConnector.save(any(MyExchange.class))).thenReturn(myExchange);

        // when
        // Todo : 자기꺼 구매하길래 임의로 바꿔둠. 수정 요망
        MyExchangeResponse response = exchangeService.doExchange(exchangeId, 2L, myExchangeRequest);

        // then
        Assertions.assertThat(response.contact()).isEqualTo(myExchangeRequest.contact());
    }

    @Test
    void 내_구매하기_목록() {
        // given
        when(myExchangeConnector.findByUserIdAndTradeType(userId, TradeType.BUY, pageable)).thenReturn(myExchanges);

        // when
        Page<MyExchangeResponse> myBuyExchanges = exchangeService.getAllMyExchange(TradeType.BUY, userId, page, size);

        // then
        verify(myExchangeConnector).findByUserIdAndTradeType(userId, TradeType.BUY, pageable);
        assertNotNull(myBuyExchanges);
        assertEquals(1, myBuyExchanges.getSize());
    }

    @Test
    void 내_판매하기_목록() {
        // given
        when(myExchangeConnector.findByUserIdAndTradeType(userId, TradeType.SELL, pageable)).thenReturn(myExchanges);

        // when
        Page<MyExchangeResponse> mySellExchanges = exchangeService.getAllMyExchange(TradeType.SELL, userId, page, size);

        // then
        verify(myExchangeConnector).findByUserIdAndTradeType(userId, TradeType.SELL, pageable);
        assertNotNull(mySellExchanges);
        assertEquals(1, mySellExchanges.getSize());
    }

    @Test
    void 거래_상태_변경_및_처리_로직() {
        // given
        when(myExchangeConnector.findById(1L)).thenReturn(myExchange);

        // when
        exchangeService.decisionMyExchange(1L, userId, putCanceledMyExchangeRequest);

        // then
        Assertions.assertThat(myExchange.getExchange().getStatusType()).isEqualTo(StatusType.CANCELED);
        verify(myExchangeConnector).save(myExchange);
    }
    /// --- MyExchange ✅ ---

    /// --- MyExchange ❌ ---
    @Test
    void 거래_중복_요청_예외() {
        // given
        when(myExchangeConnector.existsByExchangeIdAndUserId(exchangeId, userId)).thenReturn(true);

        // when & then
        DuplicateException exception = assertThrows(DuplicateException.class, () -> {
            exchangeService.doExchange(exchangeId, userId, myExchangeRequest);
        });

        assertEquals(ErrorCode.DUPLICATE, exception.getErrorCode());
    }

    @Test
    void 거래_완료시_이미_진행중_예외() {
        // given
        when(redisLockService.acquireLock(exchangeId)).thenReturn(false);

        // when & then
        InvalidRequestException exception = assertThrows(InvalidRequestException.class, () -> {
            exchangeService.completeExchange(exchangeId);
        });

        assertEquals(ErrorCode.ALREADY_IN_PROGRESS, exception.getErrorCode());
    }
    /// --- MyExchange ❌ ---

    /// --- Redis ✅ ---
    @Test
    void 거래_완료시_Redis_락_획득과_해제() {
        // given
        when(redisLockService.acquireLock(exchangeId)).thenReturn(true); // 락 획득 성공
        when(exchangeConnector.findById(exchangeId)).thenReturn(exchange);
        doNothing().when(exchangeRankingService).increaseTradeCount(anyString(), anyBoolean()); // 거래 랭킹 업데이트 목 처리

        // when
        exchangeService.completeExchange(exchangeId);

        // then
        verify(redisLockService).acquireLock(exchangeId); // 락 획득 검증
        verify(exchangeRankingService).increaseTradeCount(exchange.getGame().getGameTitle(), exchange.getTradeType() == TradeType.BUY); // 거래 랭킹 업데이트 검증
        verify(redisLockService).releaseLock(exchangeId); // 락 해제 검증
    }
    /// --- Redis ✅ ---

    /// --- Redis ❌---
    @Test
    void 거래_완료시_Redis_락_획득_실패시_예외발생() {
        // given
        when(redisLockService.acquireLock(exchangeId)).thenReturn(false); // 락 획득 실패

        // when & then
        InvalidRequestException exception = assertThrows(InvalidRequestException.class, () -> {
            exchangeService.completeExchange(exchangeId);
        });

        assertEquals("거래 완료 처리가 이미 진행 중입니다.", exception.getMessage());
        verify(redisLockService, never()).releaseLock(exchangeId); // 락 해제 호출되지 않음 검증
    }
    /// --- Redis ❌---

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
