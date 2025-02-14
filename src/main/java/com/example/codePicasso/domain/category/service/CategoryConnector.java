package com.example.codePicasso.domain.category.service;

import com.example.codePicasso.domain.category.entity.Category;
import com.example.codePicasso.domain.post.dto.response.GetAllCategoryByGameIdResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CategoryConnector {
    Category findById(Long categoryId);

    Category save(Category createCategory);

    List<GetAllCategoryByGameIdResponse> findCategoryByGameId(Long gameId);
}
