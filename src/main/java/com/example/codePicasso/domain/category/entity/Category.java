package com.example.codePicasso.domain.category.entity;

import com.example.codePicasso.domain.category.dto.response.CategoryResponse;
import com.example.codePicasso.domain.game.entity.Game;
import com.example.codePicasso.global.common.TimeStamp;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
@Builder
public class Category extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    private String categoryName;

    public static Category toEntity(Game game, String categoryName) {
        return Category.builder()
                .game(game)
                .categoryName(categoryName)
                .build();
    }
}
