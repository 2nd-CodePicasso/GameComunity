package com.example.codePicasso.domain.exchange.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class TradeCount {
    @Id
    private Long gameId;

    private Long count;

    public TradeCount(Long gameId, Long count) {
        this.gameId = gameId;
        this.count = count;
    }

    public void incrementCount() {
        this.count++;
    }
}
