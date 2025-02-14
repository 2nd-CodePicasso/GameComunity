package com.example.codePicasso.domain.posts.repository;

import com.example.codePicasso.domain.posts.dto.response.GetGameIdAllPostsResponse;
import com.example.codePicasso.domain.posts.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("""
            SELECT new com.example.codePicasso.domain.posts.dto.response.GetGameIdAllPostsResponse
                        (p.id, u.nickname, p.game.id, c.categoryName, p.title, p.createdAt)
                        FROM Post p
                        LEFT JOIN Categories c on p.categories.id = c.id
                        LEFT JOIN User u on p.user.id = u.id
                        WHERE p.game.id = :gameId
            """)
    List<GetGameIdAllPostsResponse> findPostByGameId(Long gameId);

    List<Post> findPostByCategoryId(Long categoryId);

    Optional<Post> findByUserIdAndPostId(Long postId, Long userId);
}
