package com.example.codePicasso.domain.post.service;

import com.example.codePicasso.domain.post.entity.Post;
import com.example.codePicasso.domain.post.enums.PostStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PostConnector {
    Post save(Post post);

    List<Post> findPostByGameId(Long gameId);

    List<Post> findPostByCategoryId(Long categoryId);

    List<Post> findAllByStatus(PostStatus postStatus);

    Post findById(Long postId);

    Post findByIdAndUserId(Long postId, Long userId);

    void delete(Post deletePost);
}
