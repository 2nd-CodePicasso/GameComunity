package com.example.codePicasso.domain.post.repository;

import com.example.codePicasso.domain.post.dto.response.PostResponse;
import com.example.codePicasso.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("""
            SELECT new com.example.codePicasso.domain.post.dto.response.PostResponse
                (p.id, g.id, c.id, c.categoryName, u.nickname, p.title, p.description, p.createdAt, p.updatedAt)
            FROM Post p
            LEFT JOIN p.category c
            LEFT JOIN p.user u
            LEFT JOIN p.game g
            WHERE g.id = :gameId
            """)
    List<PostResponse> findPostByGameId(Long gameId);

    @Query("""
            SELECT new com.example.codePicasso.domain.post.dto.response.PostResponse
                (p.id, g.id, c.id, c.categoryName, u.nickname, p.title, p.description, p.createdAt, p.updatedAt)
            FROM Post p
            LEFT JOIN p.category c
            LEFT JOIN p.user u
            LEFT JOIN p.game g
            WHERE c.id = :categoryId AND g.id = :gameId
            """)
    List<Post> findPostByCategoryId(Long categoryId);

    @Query("""
            SELECT new com.example.codePicasso.domain.post.dto.response.PostResponse
                    (p.id, g.id, c.id, c.categoryName, u.nickname, p.title, p.description, p.createdAt, p.updatedAt)
            FROM Post p
            LEFT JOIN p.category c
            LEFT JOIN p.game g
            LEFT JOIN p.user u
            WHERE p.game.id = :gameId AND p.id = :postId
            """)
    Optional<Post> findByGameIdAndPostId(Long postId);

    @Query("""
            SELECT p
            FROM Post p
            LEFT JOIN p.category c
            LEFT JOIN p.user u
            LEFT JOIN p.game g
            WHERE p.id = :postId AND g.id = :gameId AND u.id = :userId
            """)
    Optional<Post> findByUserIdAndPostId(Long postId, Long userId);
}
