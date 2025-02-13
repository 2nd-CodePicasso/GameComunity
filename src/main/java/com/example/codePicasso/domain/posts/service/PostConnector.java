package com.example.codePicasso.domain.posts.service;

import com.example.codePicasso.domain.posts.dto.response.GetGameIdAllPostsResponse;
import com.example.codePicasso.domain.posts.entity.Post;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface PostConnector {

    Post save(Post post);

    List<GetGameIdAllPostsResponse> findPostByGameId(Long gameId);

    List<Post> findPostByCategoryId(Long categoryId);

    Optional<Post> findById(Long postId);

    Optional<Post> findByUserIdAndPostId(Long postId, Long userId);

    void delete(Post deletePost);


}
