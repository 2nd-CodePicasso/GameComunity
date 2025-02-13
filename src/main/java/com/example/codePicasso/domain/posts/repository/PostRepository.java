package com.example.codePicasso.domain.posts.repository;

import com.example.codePicasso.domain.posts.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findPostByGameId(Long gameId);

    List<Post> findPostByCategoryId(Long categoryId);

    Optional<Post> findByUserIdAndPostId(Long postId, Long userId);
}
