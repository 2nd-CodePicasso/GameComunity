package com.example.codePicasso.domain.post.repository;

import com.example.codePicasso.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByGameId(Long gameId);

    List<Post> findAllByCategoryId(Long categoryId);

    @Query("""
            SELECT p
            FROM Post p 
            WHERE p.user.id = :userId AND p.id = :postId
            """)
    Optional<Post> findByUserIdAndPostId(Long postId, Long userId);
}
