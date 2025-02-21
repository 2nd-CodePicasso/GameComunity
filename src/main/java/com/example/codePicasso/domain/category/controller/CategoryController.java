package com.example.codePicasso.domain.category.controller;

import com.example.codePicasso.domain.category.dto.request.CategoryRequest;
import com.example.codePicasso.domain.category.dto.response.CategoryListResponse;
import com.example.codePicasso.domain.category.dto.response.CategoryResponse;
import com.example.codePicasso.domain.category.service.CategoryService;
import com.example.codePicasso.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/games/{gameId}/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(
            @PathVariable("gameId") Long gameId,
            CategoryRequest request
    ) {
        return ApiResponse.created(categoryService.createCategory(gameId, request));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<CategoryListResponse>> getCategory(
            @PathVariable("gameId") Long gameId
    ) {
        CategoryListResponse categoryListResponse = categoryService.getAllCategory(gameId);
        return ApiResponse.success(categoryListResponse);
    }

    @PatchMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(
            @PathVariable("categoryId") Long categoryId,
            CategoryRequest request
    ) {
        return ApiResponse.success(categoryService.updateCategory(categoryId, request.categoryName()));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(
            @PathVariable("categoryId") Long categoryId
    ) {
        categoryService.deleteCategory(categoryId);
        return ApiResponse.noContent();
    }
}