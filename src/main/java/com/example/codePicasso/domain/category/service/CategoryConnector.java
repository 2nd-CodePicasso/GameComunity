package com.example.codePicasso.domain.category.service;

import com.example.codePicasso.domain.category.entity.Category;
import org.springframework.stereotype.Component;

@Component
public interface CategoryConnector {

    Category findById(Long aLong);

    void save(Category createCategory);
}
