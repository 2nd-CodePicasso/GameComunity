package com.example.codePicasso.domain.category.dto.response;

import lombok.Builder;

@Builder
public record CategoryResponse(
        Long gameId,
        Long categoryId,
        String categoryName
) {
}
