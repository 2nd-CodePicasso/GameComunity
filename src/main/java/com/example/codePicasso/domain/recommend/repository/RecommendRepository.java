package com.example.codePicasso.domain.recommend.repository;

import com.example.codePicasso.domain.recommend.entity.Recommend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendRepository extends JpaRepository<Recommend, Long> {

    Integer countByPostId(Long postId);

    Recommend findByPostIdAndUserId(Long postId, Long userId);
}
