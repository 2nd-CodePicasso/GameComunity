package com.example.codePicasso.domain.category.dto.request;

import com.example.codePicasso.domain.category.entity.Category;
import com.example.codePicasso.domain.game.entity.Game;
import com.example.codePicasso.domain.users.entity.User;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CategoryCreateRequest (
        @NotBlank
        @Length(max = 10)
        String categoryName
) {
    public Category toEntity(User user, Game game) {
        return null;
    }
}
