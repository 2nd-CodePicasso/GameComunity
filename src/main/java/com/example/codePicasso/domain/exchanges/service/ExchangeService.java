package com.example.codePicasso.domain.exchanges.service;

import com.example.codePicasso.domain.exchanges.dto.request.ExchangeRequest;
import com.example.codePicasso.domain.exchanges.dto.response.ExchangeResponse;
import com.example.codePicasso.domain.exchanges.entity.Exchange;
import com.example.codePicasso.domain.exchanges.repository.ExchangeRepository;
import com.example.codePicasso.domain.games.entity.Games;
import com.example.codePicasso.domain.games.repository.GamesRepository;
import com.example.codePicasso.domain.users.entity.User;
import com.example.codePicasso.domain.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeService {

    private final ExchangeRepository exchangeRepository;
    private final GamesRepository gamesRepository;
    private final UserRepository userRepository;

    //게시글 생성
    @Transactional
    public ExchangeResponse createExchange(ExchangeRequest request) {

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new IllegalArgumentException("에러 처리 필요"));

        Games games = gamesRepository.findById(request.gameId())
                .orElseThrow(() -> new IllegalArgumentException("에러 처리 필요"));

        Exchange exchange = request.toEntity(user, games);
        Exchange savedExchange = exchangeRepository.save(exchange);
        return ExchangeResponse.fromEntity(savedExchange);
    }

    //전체 게임의 게시글 목록 조회
    @Transactional(readOnly = true)
    public List<ExchangeResponse> getExchanges() {
        List<Exchange> exchanges = exchangeRepository.findAll();
        return exchanges.stream()
                .map(ExchangeResponse::fromEntity)
                .collect(Collectors.toList());
    }

    //특정 게임의 게시글 목록 조회
    @Transactional(readOnly = true)
    public List<ExchangeResponse> getExchangesByGameId(Long gameId) {
        List<Exchange> exchanges = exchangeRepository.findByGameId(gameId);
        return exchanges.stream()
                .map(ExchangeResponse::fromEntity)
                .collect(Collectors.toList());
    }

    // 게시글 수정
    @Transactional
    public ExchangeResponse updateExchange(Long exchangeId, ExchangeRequest request) {
        Exchange exchange = exchangeRepository.findById(exchangeId).orElse(null);
        exchange.update(request.title(), request.price());
        Exchange updatedExchange = exchangeRepository.save(exchange);
        return ExchangeResponse.fromEntity(updatedExchange);
    }

    // 게시글 삭제
    @Transactional
    public void deleteExchange(Long exchangeId) {
        exchangeRepository.deleteById(exchangeId);
    }

}
