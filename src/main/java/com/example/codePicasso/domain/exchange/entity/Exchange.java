package com.example.codePicasso.domain.exchange.entity;

import com.example.codePicasso.domain.game.entity.Game;
import com.example.codePicasso.domain.user.entity.User;
import com.example.codePicasso.global.common.TimeStamp;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "exchange",
    indexes = {
        @Index(name = "idx_user_id", columnList = "user_id"),
        @Index(name = "idx_game_id", columnList = "game_id")
    }
)
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
    private Game game;

    private String title;

    private int price;
    private String description;
    private int quantity;
    private String contact;

    @Enumerated(EnumType.STRING)
    private TradeType tradeType;

    private boolean isCompleted;

    public void update(String title, int price) {
        this.title = title;
        this.price = price;
    }

    public void completed(){
        this.isCompleted = true;
    }
}
