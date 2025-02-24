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
import com.example.codePicasso.global.exception.base.DataAccessException;
import com.example.codePicasso.global.exception.base.InvalidRequestException;
import com.example.codePicasso.global.exception.base.NotFoundException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExchangeService {

    private final ExchangeConnector exchangeConnector;
    private final MyExchangeConnector myExchangeConnector;
    private final GameConnector gameConnector;
    private final UserConnector userConnector;
    private final ExchangeRankingService exchangeRankingService;
    private final RedisLockService redisLockService;

    // 거래소 아이템 생성
    @Transactional
    public ExchangeResponse createExchange(ExchangeRequest request, TradeType tradeType, Long userId) {
        User user = userConnector.findById(userId);
        Game game = gameConnector.findById(request.gameId());

        Exchange exchange = request.toEntity(user, game, tradeType);
        Exchange savedExchange = exchangeConnector.save(exchange);

        return DtoFactory.toExchangeDto(savedExchange);
    }

    // 거래소 아이템 조회_구매 (페이지네이션 적용)
    public Page<ExchangeResponse> getBuyExchanges(Long gameId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Exchange> exchanges = (gameId == null)
                ? exchangeConnector.findByTradeType(TradeType.BUY, pageable)
                : exchangeConnector.findByGameIdAndTradeType(gameId, TradeType.BUY, pageable);

        return exchanges.map(DtoFactory::toExchangeDto);
    }

    // 거래소 아이템 조회_판매 (페이지네이션 적용)
    public Page<ExchangeResponse> getSellExchanges(Long gameId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Exchange> exchanges = (gameId == null)
                ? exchangeConnector.findByTradeType(TradeType.SELL, pageable)
                : exchangeConnector.findByGameIdAndTradeType(gameId, TradeType.SELL, pageable);

        return exchanges.map(DtoFactory::toExchangeDto);
    }

    // 거래소 아이템 조회_특정 아이템
    public ExchangeResponse getExchangeById(Long exchangesId) {
        Exchange exchange = exchangeConnector.findById(exchangesId);
        return DtoFactory.toExchangeDto(exchange);
    }

    // 거래소 아이템 수정
    @Transactional
    public ExchangeResponse updateExchange(Long exchangeId, ExchangeRequest request, Long userId) {
        Exchange exchange = exchangeConnector.findById(exchangeId);

        if (!exchange.getUser().getId().equals(userId)) {
            throw new NotFoundException(ErrorCode.USER_NOT_FOUND);
        }

        exchange.update(request.title(), request.price());

        return DtoFactory.toExchangeDto(exchange);
    }

    // 거래소 아이템 삭제
    @Transactional
    public void deleteExchange(Long exchangeId, Long userId) {
        Exchange exchange = exchangeConnector.findById(exchangeId);

        if (!exchange.getUser().getId().equals(userId)) {
            throw new NotFoundException(ErrorCode.USER_NOT_FOUND);
        }

        exchangeConnector.deleteById(exchangeId);
    }

    // 판매하기 & 구매하기
    @Transactional
    public MyExchangeResponse doExchange(Long exchangeId, Long userId, MyExchangeRequest request) {
        Exchange exchange = exchangeConnector.findById(exchangeId);
        User user = userConnector.findById(userId);

        if (exchange.getStatusType().equals(StatusType.PROGRESS)) {
            throw new InvalidRequestException(ErrorCode.ALREADY_IN_PROGRESS);
        }

        exchange.changeStatus(StatusType.PROGRESS);
        Exchange savedExchange = exchangeConnector.save(exchange);

        MyExchange myExchange = request.toEntity(savedExchange, user);
        MyExchange savedMyExchange = myExchangeConnector.save(myExchange);

        return DtoFactory.toMyExchangeDto(savedMyExchange);
    }

    //내 구매하기 목록
    public Page<MyExchangeResponse> getMyBuyExchanges(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<MyExchange> myExchanges = myExchangeConnector.findByUserIdAndTradeType(userId, TradeType.BUY, pageable);

        return myExchanges.map(DtoFactory::toMyExchangeDto);
    }

    //내 판매하기 목록
    public Page<MyExchangeResponse> getMySellExchanges(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<MyExchange> myExchanges = myExchangeConnector.findByUserIdAndTradeType(userId, TradeType.SELL, pageable);

        return myExchanges.map(DtoFactory::toMyExchangeDto);
    }

    //거래 상태 변경 및 처리 로직.
    @Transactional
    public void putExchange(Long myExchangeId, Long userId, PutExchangeRequest putExchangeRequest) {
        MyExchange myExchange = myExchangeConnector.findById(myExchangeId);

        if (!myExchange.getUser().getId().equals(userId)) {
            throw new NotFoundException(ErrorCode.USER_NOT_FOUND);
        }

        // 거래가 이미 완료된 경우 중복 요청 방지
        if (myExchange.getExchange().getStatusType() == StatusType.COMPLETED) {
            throw new InvalidRequestException(ErrorCode.ALREADY_IN_COMPLETED);
        }

        myExchange.getExchange().changeStatus(putExchangeRequest.statusType());
        myExchangeConnector.save(myExchange);

        if (putExchangeRequest.statusType() == StatusType.COMPLETED) {
            completeExchange(myExchange.getExchange().getId());
        }
    }


    @Transactional
    public void completeExchange(Long exchangeId) {
        // Redis pub/sub 기반 락 획득 시도
        if (!redisLockService.acquireLock(exchangeId)) {
            throw new InvalidRequestException(ErrorCode.ALREADY_IN_PROGRESS);
        }

        Exchange exchange = exchangeConnector.findById(exchangeId);

        //Redis 랭킹 처리 (buy, sell)
        boolean isBuy = exchange.getTradeType() == TradeType.BUY;

        try {
            exchangeRankingService.increaseTradeCount(exchange.getGame().getGameTitle(), isBuy);
        } catch (Exception e) {
            log.error("Redis 업데이트 실패: gameTitle={}, isBuy={}", exchange.getGame().getGameTitle(), isBuy, e);
            throw new DataAccessException(ErrorCode.TRADE_RANKING_UPDATE_FAILED) {
            };
        } finally {
            redisLockService.releaseLock(exchangeId);
        }
    }
}
