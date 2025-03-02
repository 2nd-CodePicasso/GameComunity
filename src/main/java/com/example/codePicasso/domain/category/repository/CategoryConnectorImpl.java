package com.example.codePicasso.domain.category.repository;

import com.example.codePicasso.domain.category.entity.Category;
import com.example.codePicasso.domain.category.service.CategoryConnector;
import com.example.codePicasso.global.exception.base.InvalidRequestException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.codePicasso.domain.category.entity.QCategory.category;
import static com.example.codePicasso.domain.game.entity.QGame.game;

@Component
@RequiredArgsConstructor
public class CategoryConnectorImpl implements CategoryConnector {
    private final CategoryRepository categoryRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public Category findById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new InvalidRequestException(ErrorCode.CATEGORY_NOT_FOUND));
    }

    @Override
    public Category save(Category createCategory) {
        return categoryRepository.save(createCategory);
    }

    @Override
    public List<Category> findCategoryByGameId(Long gameId) {
        List<Category> foundCategory = queryFactory.select(category)
                .from(category)
                .leftJoin(category.game, game).fetchJoin()
                .where(category.game.id.eq(gameId))
                .fetch();
        if (foundCategory.isEmpty()) {
            throw new InvalidRequestException(ErrorCode.CATEGORY_NOT_FOUND);
        }
        return foundCategory;
    }

    @Override
    public void delete(Category deleteCategory) {
        categoryRepository.delete(deleteCategory);
    }
}