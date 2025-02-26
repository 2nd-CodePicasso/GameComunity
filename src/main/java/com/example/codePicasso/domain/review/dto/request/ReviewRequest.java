package com.example.codePicasso.domain.review.dto.request;

import com.example.codePicasso.domain.exchange.entity.Exchange;
import com.example.codePicasso.domain.review.entity.Review;
import com.example.codePicasso.domain.user.entity.User;
import jakarta.validation.constraints.NotNull;

public record ReviewRequest(
        @NotNull(message = "평점을 입력해주세요.")
        int rating,
        String review
) {
    public Review toEntity(Exchange exchange, User user) {
        return Review.builder()
                .exchange(exchange)
                .user(user)
                .rating(rating)
                .review(review)
                .build();
    }
}
