package com.example.codePicasso.domain.exchange.entity;

import com.example.codePicasso.domain.exchange.dto.response.ExchangeResponse;
import com.example.codePicasso.domain.games.entity.Games;
import com.example.codePicasso.domain.users.entity.User;
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
public class Exchange extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false)
    private Games games;

    private String title;

    private int quantity;
    private int price;
    private String description;

    @Enumerated(EnumType.STRING)
    private TradeType tradeType;

    @Enumerated(EnumType.STRING)
    private StatusType statusType;

    public void update(String title, int price) {
        this.title = title;
        this.price = price;
    }
}
