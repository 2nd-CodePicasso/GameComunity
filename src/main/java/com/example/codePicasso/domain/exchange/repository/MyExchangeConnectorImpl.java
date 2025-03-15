package com.example.codePicasso.domain.exchange.repository;

import com.example.codePicasso.domain.exchange.dto.response.MyExchangeResponse;
import com.example.codePicasso.domain.exchange.dto.response.QMyExchangeResponse;
import com.example.codePicasso.domain.exchange.entity.MyExchange;
import com.example.codePicasso.domain.exchange.entity.QMyExchange;
import com.example.codePicasso.domain.exchange.entity.TradeType;
import com.example.codePicasso.domain.exchange.service.MyExchangeConnector;
import com.example.codePicasso.global.exception.base.NotFoundException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MyExchangeConnectorImpl implements MyExchangeConnector {
    private final MyExchangeRepository myExchangeRepository;
    private final JPAQueryFactory queryFactory;
// Todo 왜 MyExchange 부분만 DTO가 아닌 객체인가...?
    @Override
    public MyExchange save(MyExchange myExchange) {
        return myExchangeRepository.save(myExchange);
    }

    @Override
    public List<MyExchange> findByUserId(Long userId) {
        return myExchangeRepository.findByUserId(userId);
    }

    @Override
    public Page<MyExchangeResponse> findByUserIdAndTradeType(Long userId, TradeType tradeType, Pageable pageable) {
        QMyExchange myExchange = QMyExchange.myExchange;

        List<MyExchangeResponse> results = queryFactory
            .select(new QMyExchangeResponse(
                myExchange.id,
                myExchange.exchange.id,
                myExchange.user.id,
                myExchange.contact,
                myExchange.statusType
            ))
            .from(myExchange)
            .where(myExchange.user.id.eq(userId)
                .and(myExchange.exchange.tradeType.eq(tradeType)))
            .orderBy(myExchange.id.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        JPAQuery<Long> countQuery = queryFactory
            .select(myExchange.count())
            .from(myExchange)
            .where(myExchange.user.id.eq(userId)
                .and(myExchange.exchange.tradeType.eq(tradeType)));

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
    }

    @Override
    public MyExchange findById(Long myExchangeId) {
        return myExchangeRepository.findById(myExchangeId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.MYEXCHANGE_NOT_FOUND));
    }

    @Override
    public boolean existByExchangeIdAndUserId(Long exchangeId, Long userId) {
        return myExchangeRepository.existsByExchangeIdAndUserId(exchangeId, userId);
    }

    @Override
    public MyExchange findByExchangeIdAndUserId(Long exchangeId, Long userId) {
        return myExchangeRepository.findByExchangeIdAndUserId(exchangeId, userId);
    }
}
