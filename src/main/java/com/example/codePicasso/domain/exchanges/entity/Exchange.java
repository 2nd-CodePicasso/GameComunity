package com.example.codePicasso.domain.exchanges.entity;

import com.example.codePicasso.domain.exchanges.dto.response.ExchangeResponse;
import com.example.codePicasso.domain.games.entity.Games;
import com.example.codePicasso.domain.users.entity.User;
import com.example.codePicasso.global.common.TimeStamp;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

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

    private int price;
    private String description;
    private int quantity;

    @CreationTimestamp
    private LocalDateTime created_at;

    @UpdateTimestamp
    private LocalDateTime updated_at;

//    public ExchangeResponse toDto() {
//        return ExchangeResponse.builder()
//                .id(id)
//                .gameId(games.getId())
//                .title(title)
//                .price(price)
//                .build();
//    }
//
    public void update(String title, int price) {
        this.title = title;
        this.price = price;
    }
}
