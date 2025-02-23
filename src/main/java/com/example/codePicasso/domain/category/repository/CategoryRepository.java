package com.example.codePicasso.domain.category.repository;

import com.example.codePicasso.domain.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByGameId(Long gameId);
}
