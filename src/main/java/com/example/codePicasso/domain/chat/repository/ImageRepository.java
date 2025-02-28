package com.example.codePicasso.domain.chat.repository;

import com.example.codePicasso.domain.chat.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,Long> {
    boolean existsByImageUrlAndUserId(String imageUrl, Long userId);
}
