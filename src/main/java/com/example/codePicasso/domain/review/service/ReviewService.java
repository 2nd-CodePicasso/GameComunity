package com.example.codePicasso.domain.review.service;

import com.example.codePicasso.domain.exchange.entity.MyExchange;
import com.example.codePicasso.domain.exchange.entity.StatusType;
import com.example.codePicasso.domain.exchange.service.MyExchangeConnector;
import com.example.codePicasso.domain.review.dto.request.ReviewRequest;
import com.example.codePicasso.domain.review.dto.response.ReviewResponse;
import com.example.codePicasso.domain.review.entity.Review;
import com.example.codePicasso.global.common.DtoFactory;
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
public class ReviewService {
    private final ReviewConnector reviewConnector;
    private final MyExchangeConnector myExchangeConnector;

    @Transactional
    public ReviewResponse createReview(Long exchangeId, Long userId, ReviewRequest request) {
        MyExchange myExchange = myExchangeConnector.findByExchangeIdAndUserId(exchangeId, userId);

        if (myExchange.getStatusType() != StatusType.COMPLETED) {
            throw new InvalidRequestException(ErrorCode.NOT_COMPLETED);
        }

        Review review = request.toEntity(myExchange.getExchange(), myExchange.getUser());
        Review savedReview = reviewConnector.save(review);

        return DtoFactory.toReviewDto(savedReview);
    }

    public Page<ReviewResponse> getReviews(Long exchangeId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Review> reviews = reviewConnector.findAllByExchangeId(exchangeId, pageable);

        return reviews.map(DtoFactory::toReviewDto);
    }

    public ReviewResponse getReviewById(Long exchangeId, Long reviewId) {
        Review review = reviewConnector.findById(reviewId);

        if (!review.getExchange().getId().equals(exchangeId)) {
            throw new NotFoundException(ErrorCode.EXCHANGE_NOT_FOUND);
        }

        return DtoFactory.toReviewDto(review);
    }

    @Transactional
    public ReviewResponse updateReview(Long exchangeId, Long reviewId, Long userId, ReviewRequest request) {
        Review review = reviewConnector.findById(reviewId);

        if (!review.getExchange().getId().equals(exchangeId)) {
            throw new NotFoundException(ErrorCode.EXCHANGE_NOT_FOUND);
        }

        if (!review.getUser().getId().equals(userId)) {
            throw new NotFoundException(ErrorCode.USER_NOT_FOUND);
        }

        review.updateReview(request.rating(), request.review());

        return DtoFactory.toReviewDto(review);
    }

    @Transactional
    public void deleteReview(Long exchangeId, Long reviewId, Long userId) {
        Review review = reviewConnector.findById(reviewId);

        if (!review.getExchange().getId().equals(exchangeId)) {
            throw new NotFoundException(ErrorCode.EXCHANGE_NOT_FOUND);
        }

        if (!review.getUser().getId().equals(userId)) {
            throw new NotFoundException(ErrorCode.USER_NOT_FOUND);
        }

        reviewConnector.delete(review);
    }
}
