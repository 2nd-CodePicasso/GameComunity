package com.example.codePicasso.domain.category.service;

import com.example.codePicasso.domain.category.dto.request.CategoryRequest;
import com.example.codePicasso.domain.category.dto.response.CategoryListResponse;
import com.example.codePicasso.domain.category.dto.response.CategoryResponse;
import com.example.codePicasso.domain.category.entity.Category;
import com.example.codePicasso.domain.game.entity.Game;
import com.example.codePicasso.domain.game.service.GameConnector;
import com.example.codePicasso.global.common.DtoFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {
    private final CategoryConnector categoryConnector;
    private final GameConnector gameConnector;

    @Transactional
    public CategoryResponse createCategory(Long gameId, CategoryRequest request) {
        Game game = gameConnector.findByIdForUser(gameId);
        Category createCategory = request.toEntity(game);
        Category saveCategory = categoryConnector.save(createCategory);
        return DtoFactory.toCategoryDto(saveCategory);
    }

    public CategoryListResponse getAllCategory(Long gameId) {
        List<CategoryResponse> categoryResponses = categoryConnector.findCategoryByGameId(gameId).stream()
                .map(DtoFactory::toCategoryDto).toList();
        return CategoryListResponse.builder()
                .categoryResponses(categoryResponses)
                .build();
    }

    @Transactional
    public CategoryResponse updateCategory(Long categoryId, CategoryRequest request) {
        Category foundCategory = categoryConnector.findById(categoryId);

        foundCategory.updateCategory(request.categoryName());

        return DtoFactory.toCategoryDto(foundCategory);
    }

    @Transactional
    public void deleteCategory(Long categoryId) {
        Category deleteCategory = categoryConnector.findById(categoryId);

        categoryConnector.delete(deleteCategory);

    }
}
