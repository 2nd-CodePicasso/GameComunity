package com.example.codePicasso.domain.review.service;

import com.example.codePicasso.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public interface ReviewConnector {
    Review save(Review review);

    Page<Review> findAllByExchangeId(Long exchangeId, Pageable pageable);

    Review findById(Long reviewId);

    void delete(Review review);
}
