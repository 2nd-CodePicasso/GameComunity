package com.example.codePicasso.domain.exchange.service;

import com.example.codePicasso.domain.exchange.entity.Review;
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
