package com.example.codePicasso.domain.game.entity;

import com.example.codePicasso.domain.game.dto.response.GameResponse;
import com.example.codePicasso.domain.user.entity.Admin;
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
public class Game extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

    @Column(nullable = false)
    private String gameTitle;

    @Column(nullable = false)
    private String gameDescription;

    @Column(nullable = false)
    private boolean isDeleted;

    public GameResponse toDto() {
        return GameResponse.builder()
                .id(id)
                .gameTitle(gameTitle)
                .gameDescription(gameDescription)
                .created_at(getCreatedAt())
                .updated_at(getUpdatedAt())
                .build();
    }

    public void updateDetails(String gameDescription) {
        this.gameDescription = gameDescription;
    }

    public void deleteGame() {
        isDeleted = true;
    }

    public void restore() {
        isDeleted = false;
    }
}
