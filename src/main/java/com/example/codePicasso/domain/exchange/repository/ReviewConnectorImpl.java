package com.example.codePicasso.domain.exchange.repository;

import com.example.codePicasso.domain.exchange.entity.Review;
import com.example.codePicasso.domain.exchange.service.ReviewConnector;
import com.example.codePicasso.global.exception.base.NotFoundException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewConnectorImpl implements ReviewConnector {
    private final ReviewRepository reviewRepository;

    @Override
    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public Page<Review> findAllByExchangeId(Long exchangeId, Pageable pageable) {
        return reviewRepository.findAllByExchangeId(exchangeId, pageable);
    }

    @Override
    public Review findById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(() -> new NotFoundException(ErrorCode.REVIEW_NOT_FOUND));
    }

    @Override
    public void delete(Review review) {
        reviewRepository.delete(review);
    }
}
