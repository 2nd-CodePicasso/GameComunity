package com.example.codePicasso.domain.post.dto.response;

import com.example.codePicasso.domain.category.entity.Category;
import lombok.Builder;

@Builder
public record GetAllCategoryByGameIdResponse(
        Long gameId,
        Long categoryId,
        String categoryName
) {
    public static GetAllCategoryByGameIdResponse toDto(Category category) {
        return GetAllCategoryByGameIdResponse.builder()
                .gameId(category.getGame().getId())
                .categoryId(category.getId())
                .categoryName(category.getCategoryName())
                .build();
    }
}
