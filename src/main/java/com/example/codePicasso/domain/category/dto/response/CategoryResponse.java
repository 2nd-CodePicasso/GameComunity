package com.example.codePicasso.domain.category.dto.response;

import lombok.Builder;

@Builder
public record CategoryResponse (
        Long categoryId,
        String categoryName
) {
}
