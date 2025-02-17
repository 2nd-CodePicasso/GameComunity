package com.example.codePicasso.domain.post.repository;

import com.example.codePicasso.domain.post.dto.response.GetGameIdAllPostsResponse;
import com.example.codePicasso.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("""
            SELECT new com.example.codePicasso.domain.post.dto.response.GetGameIdAllPostsResponse
                        (p.id, u.nickname, p.game.id, c.categoryName, p.title, p.createdAt)
                        FROM Post p
                        LEFT JOIN Category c on p.category.id = c.id
                        LEFT JOIN User u on p.user.id = u.id
                        WHERE p.game.id = :gameId
            """)
    List<GetGameIdAllPostsResponse> findPostByGameId(Long gameId);

    List<Post> findPostByCategoryId(Long categoryId);

    @Query("""
            SELECT p
            FROM Post p 
            WHERE p.user.id = :userId AND p.id = :postId
            """)
    Optional<Post> findByUserIdAndPostId(Long postId, Long userId);
}
