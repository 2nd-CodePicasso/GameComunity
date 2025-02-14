package com.example.codePicasso.domain.category.controller;

import com.example.codePicasso.domain.category.dto.request.CategoryCreateRequest;
import com.example.codePicasso.domain.category.dto.response.CategoryResponse;
import com.example.codePicasso.domain.category.service.CategoryService;
import com.example.codePicasso.domain.post.dto.response.GetAllCategoryByGameIdResponse;
import com.example.codePicasso.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/games/{gameId}/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(
            @PathVariable Long gameId,
            CategoryCreateRequest request
    ) {
        CategoryResponse response = categoryService.createCategory(gameId, request.categoryName());
        return ApiResponse.created(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<GetAllCategoryByGameIdResponse>>> getCategory(
            @PathVariable Long gameId
    ) {
        List<GetAllCategoryByGameIdResponse> response = categoryService.getAllCategory(gameId);
        return ApiResponse.success(response);
    }
}