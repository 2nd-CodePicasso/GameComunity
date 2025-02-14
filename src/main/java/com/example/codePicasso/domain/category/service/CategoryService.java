package com.example.codePicasso.domain.category.service;

import com.example.codePicasso.domain.category.dto.response.CategoryResponse;
import com.example.codePicasso.domain.category.entity.Category;
import com.example.codePicasso.domain.game.entity.Game;
import com.example.codePicasso.domain.game.service.GameConnector;
import com.example.codePicasso.domain.post.dto.response.GetAllCategoryByGameIdResponse;
import com.example.codePicasso.domain.post.service.PostConnector;
import com.example.codePicasso.global.exception.base.InvalidRequestException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
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
    private final PostConnector postConnector;

    @Transactional
    public CategoryResponse createCategory(Long gameId, String categoryName) {
        Game game = gameConnector.findById(gameId);
        Category createCategory = Category.toEntity(game, categoryName);
        categoryConnector.save(createCategory);
        return CategoryResponse.toDto(createCategory);
    }

    public List<GetAllCategoryByGameIdResponse> getAllCategory(Long gameId) {
        return categoryConnector.findCategoryByGameId(gameId).stream()
                .map(GetAllCategoryByGameIdResponse::toDto).toList();
    }
    @Transactional
    public CategoryResponse updateCategory(Long categoryId, String categoryName) {
        Category foundCategory = categoryConnector.findByCategoryId(categoryId)
                .orElseThrow(() -> new InvalidRequestException(ErrorCode.CATEGORY_NOT_FOUND));

        foundCategory.updateCategory(categoryName);

        return CategoryResponse.toDto(foundCategory);
    }
    @Transactional
    public void deleteCategory(Long categoryId) {
        Category deleteCategory = categoryConnector.findByCategoryId(categoryId)
                .orElseThrow(() -> new InvalidRequestException(ErrorCode.CATEGORY_NOT_FOUND));

        categoryConnector.delete(deleteCategory);

    }
}
