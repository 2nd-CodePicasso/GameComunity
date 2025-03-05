package com.example.codePicasso.domain.review.entity;

import com.example.codePicasso.domain.exchange.entity.Exchange;
import com.example.codePicasso.domain.user.entity.User;
import com.example.codePicasso.global.common.TimeStamp;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "exchange_id", nullable = false)
    private Exchange exchange;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private int rating;
    private String review;

    public void updateReview(int rating, String review) {
        this.rating = rating;
        this.review = review;
    }
}
