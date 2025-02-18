package com.example.codePicasso.domain.category.dto.response;

import com.example.codePicasso.domain.category.entity.Category;
import lombok.Builder;

@Builder
public record CategoryResponse (
        Long gameId,
        Long categoryId,
        String categoryName
) {
    public static CategoryResponse toDto(Category category) {
        return CategoryResponse.builder()
                .gameId(category.getGame().getId())
                .categoryId(category.getId())
                .categoryName(category.getCategoryName())
                .build();
    }
}
