package com.example.codePicasso.domain.category.repository;

import com.example.codePicasso.domain.category.entity.Category;
import com.example.codePicasso.domain.category.service.CategoryConnector;
import com.example.codePicasso.domain.game.entity.Game;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RepositoryConnectorImpl implements CategoryConnector {
    private final CategoryRepository mCategoryRepository;
    private final CategoryConnector categoryConnector;

    @Override
    public Category findById(Category categoryId) {
        return categoryConnector.findById(categoryId);
    }

    @Override
    public Category save(Category createCategory) {
        return categoryConnector.save(createCategory);
    }
}
