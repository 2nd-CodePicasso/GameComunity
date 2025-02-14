package com.example.codePicasso.domain.category.repository;

import com.example.codePicasso.domain.category.entity.Category;
import com.example.codePicasso.domain.category.service.CategoryConnector;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RepositoryConnectorImpl implements CategoryConnector {

    private final CategoryRepository mCategoryRepository;

    // Todo 내용 채워주세용
    @Override
    public Category findById(Long aLong) {
        return null;
    }
}
