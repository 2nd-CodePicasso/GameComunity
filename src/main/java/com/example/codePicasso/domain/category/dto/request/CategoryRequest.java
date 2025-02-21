package com.example.codePicasso.domain.category.dto.request;

import com.example.codePicasso.domain.category.entity.Category;
import com.example.codePicasso.domain.game.entity.Game;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CategoryRequest(
        @NotBlank
        @Length(max = 10)
        String categoryName
) {
        public Category toEntity(Game game) {
                return Category.builder()
                        .categoryName(categoryName)
                        .game(game)
                        .build();
        }
}
