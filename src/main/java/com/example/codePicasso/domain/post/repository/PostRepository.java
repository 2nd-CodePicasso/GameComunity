package com.example.codePicasso.domain.post.repository;

import com.example.codePicasso.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
//    @EntityGraph(attributePaths = {"user", "category"})
//    Page<Post> findAllByGameId(Long gameId, Pageable pageable);

//    Page<Post> findAllByCategoryId(Long categoryId, Pageable pageable);

//    Page<Post> findAllByGameIdAndStatus(Long gameId, PostStatus status, Pageable pageable);

    Optional<Post> findByIdAndUserId(Long postId, Long userId);
}
