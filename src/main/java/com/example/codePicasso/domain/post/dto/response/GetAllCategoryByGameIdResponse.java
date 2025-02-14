package com.example.codePicasso.domain.post.dto.response;

public record GetAllCategoryByGameIdResponse (
        Long gameId,
        Long categoryId,
        String categoryName
) {
}
