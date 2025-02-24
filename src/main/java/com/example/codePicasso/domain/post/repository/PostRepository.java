package com.example.codePicasso.domain.post.repository;

import com.example.codePicasso.domain.post.entity.Post;
import com.example.codePicasso.domain.post.enums.PostStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByGameId(Long gameId);

    List<Post> findAllByCategoryId(Long categoryId);

    List<Post> findAllByStatus(PostStatus status);

    Optional<Post> findByIdAndUserId(Long postId, Long userId);
}
