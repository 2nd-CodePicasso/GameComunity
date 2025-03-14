package com.example.codePicasso.domain.exchange.repository;

import com.example.codePicasso.domain.exchange.dto.response.ExchangeResponse;
import com.example.codePicasso.domain.exchange.dto.response.QExchangeResponse;
import com.example.codePicasso.domain.exchange.entity.Exchange;
import com.example.codePicasso.domain.exchange.entity.QExchange;
import com.example.codePicasso.domain.exchange.entity.TradeType;
import com.example.codePicasso.domain.exchange.service.ExchangeConnector;
import com.example.codePicasso.global.exception.base.NotFoundException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExchangeConnectorImpl implements ExchangeConnector {
    private final ExchangeRepository exchangeRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public Exchange save(Exchange exchange) {
        return exchangeRepository.save(exchange);
    }

    @Override
    public Page<ExchangeResponse> findByTradeType(TradeType tradeType, Pageable pageable) {
        QExchange exchange = QExchange.exchange;

        List<ExchangeResponse> results = queryFactory
            .select(new QExchangeResponse(
                exchange.id,
                exchange.user.id,
                exchange.game.id,
                exchange.title,
                exchange.price,
                exchange.description,
                exchange.quantity,
                exchange.contact,
                exchange.tradeType,
                exchange.isCompleted
            ))
            .from(exchange)
            .where(exchange.tradeType.eq(tradeType).and(exchange.isCompleted.isFalse()))
            .orderBy(exchange.id.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        JPAQuery<Long> total = queryFactory
            .select(exchange.count())
            .from(exchange)
            .where(exchange.tradeType.eq(tradeType).and(exchange.isCompleted.isFalse()));

        return PageableExecutionUtils.getPage(results, pageable, total::fetchOne);
    }

    @Override
    public Page<ExchangeResponse> findByGameIdAndTradeType(Long gameId, TradeType tradeType, Pageable pageable) {
        QExchange exchange = QExchange.exchange;

        List<ExchangeResponse> results = queryFactory
            .select(new QExchangeResponse(
                exchange.id,
                exchange.user.id,
                exchange.game.id,
                exchange.title,
                exchange.price,
                exchange.description,
                exchange.quantity,
                exchange.contact,
                exchange.tradeType,
                exchange.isCompleted
            ))
            .from(exchange)
            .where(exchange.game.id.eq(gameId)
                .and(exchange.tradeType.eq(tradeType))
                .and(exchange.isCompleted.isFalse()))
            .orderBy(exchange.id.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        JPAQuery<Long> total = queryFactory
            .select(exchange.count())
            .from(exchange)
            .where(exchange.game.id.eq(gameId)
                .and(exchange.tradeType.eq(tradeType))
                .and(exchange.isCompleted.isFalse()));

        return PageableExecutionUtils.getPage(results, pageable, total::fetchOne);
    }

    @Override
    public ExchangeResponse findByIdAndIsCompleted(Long id) {
        QExchange exchange = QExchange.exchange;

        return queryFactory
            .select(new QExchangeResponse(
                exchange.id,
                exchange.user.id,
                exchange.game.id,
                exchange.title,
                exchange.price,
                exchange.description,
                exchange.quantity,
                exchange.contact,
                exchange.tradeType,
                exchange.isCompleted
            ))
            .from(exchange)
            .where(exchange.id.eq(id).and(exchange.isCompleted.isFalse()))
            .fetchOne();
    }

    @Override
    public Exchange findById(Long exchangeId) {
        return exchangeRepository.findById(exchangeId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.EXCHANGE_NOT_FOUND));
    }
}
