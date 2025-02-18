package com.example.codePicasso.domain.category.repository;

import com.example.codePicasso.domain.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("""
    SELECT new com.example.codePicasso.domain.category.dto.response.CategoryResponse
            (g.id, c.id, c.categoryName)
    FROM Category c
        LEFT JOIN c.game g
    WHERE c.game.id = :gameId
    """)
    List<Category> findByGameId(Long gameId);
}
