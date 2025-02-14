package com.example.codePicasso.domain.exchanges.service;

import com.example.codePicasso.domain.exchange.dto.request.ExchangeRequest;
import com.example.codePicasso.domain.exchange.dto.response.ExchangeResponse;
import com.example.codePicasso.domain.exchange.entity.Exchange;
import com.example.codePicasso.domain.exchange.entity.TradeType;
import com.example.codePicasso.domain.exchange.service.ExchangeConnector;
import com.example.codePicasso.domain.exchange.service.ExchangeService;
import com.example.codePicasso.domain.games.entity.Games;
import com.example.codePicasso.domain.games.service.GamesConnector;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ExchangeServiceTest {

    @Mock
    private ExchangeConnector exchangeConnector;

    @Mock
    private GamesConnector gamesConnector;

    @Mock
    private UserConnector userConnector;

    @InjectMocks
    private ExchangeService exchangeService;

    private ExchangeRequest exchangeRequest;
    private Exchange exchange;
    private Page<Exchange> exchanges;
    private User user;
    private Games games;
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
        games = mock(Games.class);
        exchangeRequest = new ExchangeRequest(1L, "거래소", 100, "거래소", 100);
        exchange = exchangeRequest.toEntity(user, games, TradeType.BUY);
        exchanges = new PageImpl<>(List.of(exchange));

        when(userConnector.findById(userId)).thenReturn(user);
        when(gamesConnector.findById(gameId)).thenReturn(games);
        when(exchangeConnector.findById(exchangeId)).thenReturn(exchange);

        when(user.getId()).thenReturn(userId);
        when(games.getId()).thenReturn(userId);
    }

    @Test
    void 거래소_게시글_만들기() {
        // given
        when(exchangeConnector.save(any(Exchange.class))).thenReturn(exchange);

        // when
        ExchangeResponse response = exchangeService.createExchange(exchangeRequest, TradeType.BUY, userId);

        // then
        Assertions.assertThat(response)
                .usingRecursiveComparison()
                .ignoringFields("id", "userId")
                .isEqualTo(exchangeRequest);
    }

    @Test
    void 거래소_게시글_가져오기_구매_Param_gameId_Yes() {
        // given
        when(exchangeConnector.findByGameIdAndTradeType(gameId, TradeType.BUY, pageable)).thenReturn(exchanges);

        // when
        Page<ExchangeResponse> responses = exchangeService.getBuyExchangesByGameId(gameId, page, size);

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
        Page<ExchangeResponse> responses = exchangeService.getSellExchangesByGameId(gameId, page, size);

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
        ExchangeRequest newReq = new ExchangeRequest(1L, "짱거래소", 1000, "거래소", 100);
        when(exchangeConnector.save(any(Exchange.class))).thenReturn(exchange);
        when(exchange.getUser().getId()).thenReturn(userId);

        // when
        ExchangeResponse response = exchangeService.updateExchange(exchangeId, newReq, userId);

        // then
        Assertions.assertThat(response)
                .usingRecursiveComparison()
                .ignoringFields("id", "userId", "gameId", "description", "quantity")
                .isEqualTo(newReq);

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

    // 예외 or 다른 로직 처리
    @Test
    void 거래소_게시글_가져오기_구매_Param_gameId_No() {
        // given
        when(exchangeConnector.findByTradeType(TradeType.BUY, pageable)).thenReturn(exchanges);

        // when
        Page<ExchangeResponse> responses = exchangeService.getBuyExchangesByGameId(null, page, size);

        // then
        verify(exchangeConnector).findByTradeType(TradeType.BUY, pageable);
        assertNotNull(responses);
        assertEquals(1, responses.getSize());
    }

    @Test
    void 거래소_게시글_삭제_유저_예외처리() {
        // given
        when(exchange.getUser().getId()).thenReturn(userId);
        Long wrongUserId = 2L;

        // when & then
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            exchangeService.deleteExchange(exchangeId, wrongUserId);
        });
        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
    }
}

