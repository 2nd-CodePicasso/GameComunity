package com.example.codePicasso.domain.category.service;

import com.example.codePicasso.domain.category.dto.request.CategoryRequest;
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
    public CategoryResponse createCategory(Long gameId, CategoryRequest category) {
        Game game = gameConnector.findById(gameId);
        Category createCategory = category.toEntity(game);
        categoryConnector.save(createCategory);
        return DtoFactory.toCategoryDto(createCategory);
    }

    public List<CategoryResponse> getAllCategory(Long gameId) {
        return categoryConnector.findCategoryByGameId(gameId).stream()
                .map(CategoryResponse::toDto).toList();
    }
    @Transactional
    public CategoryResponse updateCategory(Long categoryId, String categoryName) {
        Category foundCategory = categoryConnector.findById(categoryId);

        foundCategory.updateCategory(categoryName);

        return CategoryResponse.toDto(foundCategory);
    }
    @Transactional
    public void deleteCategory(Long categoryId) {
        Category deleteCategory = categoryConnector.findById(categoryId);

        categoryConnector.delete(deleteCategory);

    }
}
