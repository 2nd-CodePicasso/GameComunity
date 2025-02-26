package com.example.codePicasso.domain.post.service;

import com.example.codePicasso.domain.post.entity.Post;
import com.example.codePicasso.domain.post.enums.PostStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PostConnector {
    Post save(Post post);

    Page<Post> findAllByGameId(Long gameId, Pageable pageable);

    Page<Post> findAllByCategoryId(Long categoryId, Pageable pageable);

    Page<Post> findAllByStatus(PostStatus postStatus, Pageable pageable);
  
    Post findById(Long postId);

    Post findByIdAndUserId(Long postId, Long userId);

    void delete(Post deletePost);
}
