package com.example.codePicasso.domain.category.repository;

import com.example.codePicasso.domain.category.entity.Category;
import com.example.codePicasso.domain.post.dto.response.GetAllCategoryByGameIdResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryId(Long categoryId);

    List<GetAllCategoryByGameIdResponse> findByGameId(Long gameId);
}
