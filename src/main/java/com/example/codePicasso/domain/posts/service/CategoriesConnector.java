package com.example.codePicasso.domain.posts.service;

import com.example.codePicasso.domain.posts.entity.Categories;
import org.springframework.stereotype.Component;

@Component
public interface CategoriesConnector {

    Categories findById(Long aLong);
}
