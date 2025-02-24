package com.example.codePicasso.domain.category.repository;

import com.example.codePicasso.domain.category.entity.Category;
import com.example.codePicasso.domain.category.service.CategoryConnector;
import com.example.codePicasso.global.exception.base.InvalidRequestException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryConnectorImpl implements CategoryConnector {
    private final CategoryRepository categoryRepository;

    @Override
    public Category findById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new InvalidRequestException(ErrorCode.CATEGORY_NOT_FOUND));
    }

    @Override
    public Category save(Category createCategory) {
        return categoryRepository.save(createCategory);
    }

    @Override
    public List<Category> findCategoryByGameId(Long gameId) {
        return categoryRepository.findByGameId(gameId);
    }

    @Override
    public void delete(Category deleteCategory) {
        categoryRepository.delete(deleteCategory);
    }
}