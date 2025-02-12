package com.example.codePicasso.domain.exchanges.service;

import com.example.codePicasso.domain.exchanges.dto.request.ExchangeRequest;
import com.example.codePicasso.domain.exchanges.dto.response.ExchangeResponse;
import com.example.codePicasso.domain.exchanges.entity.Exchange;
import com.example.codePicasso.domain.exchanges.entity.TradeType;
import com.example.codePicasso.domain.games.entity.Games;
import com.example.codePicasso.domain.games.repository.GamesRepository;
import com.example.codePicasso.domain.users.entity.User;
import com.example.codePicasso.domain.users.repository.UserRepository;
import com.example.codePicasso.domain.users.service.UserConnector;
import com.example.codePicasso.global.exception.base.NotFoundException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExchangeService {

    private final ExchangeConnector exchangeConnector;
//    private final GamesConnector gamesConnector;
//    private final UserConnector userConnector;
    private final GamesRepository gamesRepository;
    private final UserRepository userRepository;

    // 거래소 아이템 생성
    @Transactional
    public ExchangeResponse createExchange(ExchangeRequest request, TradeType tradeType, Long userId) {
//        User user = userConnector.findById(userId);
//        Games games = gamesConnector.findById(request.gameId());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        Games games = gamesRepository.findById(request.gameId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.GAME_NOT_FOUND));

        Exchange exchange = request.toEntity(user, games, tradeType);
        Exchange savedExchange = exchangeConnector.save(exchange);
        return ExchangeResponse.fromEntity(savedExchange);
    }

//    //전체 게임의 게시글 목록 조회
//    @Transactional(readOnly = true)
//    public List<ExchangeResponse> getExchanges() {
//        List<Exchange> exchanges = exchangeConnector.findAll();
//        return exchanges.stream()
//                .map(ExchangeResponse::fromEntity)
//                .collect(Collectors.toList());
//    }

    // 거래소 아이템 조회_구매
    public List<ExchangeResponse> getBuyExchangesByGameId(Long gameId) {
        List<Exchange> exchanges = (gameId == null)
                ? exchangeConnector.findByTradeType(TradeType.BUY)
                : exchangeConnector.findByGameIdAndTradeType(gameId, TradeType.BUY);
        return exchanges.stream()
                .map(ExchangeResponse::fromEntity)
                .collect(Collectors.toList());
    }

    // 거래소 아이템 조회_판매
    public List<ExchangeResponse> getSellExchangesByGameId(Long gameId) {
        List<Exchange> exchanges = (gameId == null)
                ? exchangeConnector.findByTradeType(TradeType.SELL)
                : exchangeConnector.findByGameIdAndTradeType(gameId, TradeType.SELL);
        return exchanges.stream()
                .map(ExchangeResponse::fromEntity)
                .collect(Collectors.toList());
    }

    // 거래소 아이템 조회_특정 아이템
    public ExchangeResponse getExchangeById(Long exchangesId) {
        return ExchangeResponse.fromEntity(exchangeConnector.findById(exchangesId));
    }

    // 거래소 아이템 수정
    @Transactional
    public ExchangeResponse updateExchange(Long exchangeId, ExchangeRequest request, Long userId) {
        Exchange exchange = exchangeConnector.findById(exchangeId);

        if (!exchange.getUser().getId().equals(userId)) {
            throw new NotFoundException(ErrorCode.USER_NOT_FOUND);
        }

        exchange.update(request.title(), request.price());
        Exchange updatedExchange = exchangeConnector.save(exchange);
        return ExchangeResponse.fromEntity(updatedExchange);
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

}
