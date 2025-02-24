package com.example.codePicasso.domain.post.service;

import com.example.codePicasso.domain.post.entity.Post;
import com.example.codePicasso.domain.post.enums.PostStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PostConnector {
    Post save(Post post);

    List<Post> findAllByGameId(Long gameId);

    List<Post> findAllByCategoryId(Long categoryId);

    List<Post> findAllByStatus(PostStatus postStatus);
  
    Post findById(Long postId);

    Post findByIdAndUserId(Long postId, Long userId);

    Post findById(Long postId);

    Post findByIdAndUserId(Long postId, Long userId);

    void delete(Post deletePost);
}
