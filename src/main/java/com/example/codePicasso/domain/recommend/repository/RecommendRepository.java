package com.example.codePicasso.domain.recommend.repository;

import com.example.codePicasso.domain.recommend.entity.Recommend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecommendRepository extends JpaRepository<Recommend, Long> {

    Integer countByPostId(Long postId);

    Optional<Recommend> findByPostIdAndUserId(Long postId, Long userId);
}
