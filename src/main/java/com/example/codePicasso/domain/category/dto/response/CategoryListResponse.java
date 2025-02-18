package com.example.codePicasso.domain.category.dto.response;

import lombok.Builder;

import java.util.List;
@Builder
public record CategoryListResponse(
        List<CategoryResponse> response
) {

}
