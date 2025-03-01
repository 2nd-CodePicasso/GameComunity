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
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    /**
     * 카테고리 생성
     * @param gameId
     * @param request
     * @return
     */
    @PostMapping("/{gameId}")
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(
            @PathVariable("gameId") Long gameId,
            @RequestBody CategoryRequest request
    ) {
        return ApiResponse.created(categoryService.createCategory(gameId, request));
    }

    /**
     * gameId 내 카테고리 조회
     * @param gameId
     * @return
     */
    @GetMapping("/{gameId}")
    public ResponseEntity<ApiResponse<CategoryListResponse>> getCategory(
            @PathVariable("gameId") Long gameId
    ) {
        CategoryListResponse categoryListResponse = categoryService.getAllCategory(gameId);
        return ApiResponse.success(categoryListResponse);
    }

    /**
     * 카테고리 수정 (이름)
     * @param categoryId
     * @param request
     * @return
     */
    @PatchMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(
            @PathVariable("categoryId") Long categoryId,
            @RequestBody CategoryRequest request
    ) {
        return ApiResponse.success(categoryService.updateCategory(categoryId, request));
    }

    /**
     * 카테고리 삭제
     * @param categoryId
     * @return
     */
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(
            @PathVariable("categoryId") Long categoryId
    ) {
        categoryService.deleteCategory(categoryId);
        return ApiResponse.noContent();
    }
}