package com.example.codePicasso.domain.exchange.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TradeCount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long gameId;

    private Long count;

    @Version
    private Long version;

    @Enumerated(EnumType.STRING)
    private TradeType tradeType;

    public TradeCount(Long gameId, Long count, TradeType tradeType) {
        this.gameId = gameId;
        this.count = count;
        this.tradeType = tradeType;
    }

    public void incrementCount() {
        this.count++;
    }

}
