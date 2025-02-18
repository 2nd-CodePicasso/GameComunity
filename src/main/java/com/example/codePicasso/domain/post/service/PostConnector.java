package com.example.codePicasso.domain.post.service;

import com.example.codePicasso.domain.post.dto.response.PostResponse;
import com.example.codePicasso.domain.post.entity.Post;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface PostConnector {

    Post save(Post post);

    List<PostResponse> findPostByGameId(Long gameId);

    List<Post> findPostByCategoryId(Long gameId, Long categoryId);

    Optional<Post> findByPostId(Long gameId, Long postId);

    Optional<Post> findByUserIdAndPostId(Long gameId, Long postId, Long userId);

    void delete(Post deletePost);


}
