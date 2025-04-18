package com.example.codePicasso.domain.post.service;

import com.example.codePicasso.domain.post.dto.response.PostResponse;
import com.example.codePicasso.domain.post.entity.Post;
import com.example.codePicasso.domain.post.enums.PostStatus;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PostConnector {
    Post save(Post post);

    Page<PostResponse> findAllByGameId(Long gameId, Pageable pageable);

    Page<PostResponse> findAllByCategoryId(Long categoryId, Pageable pageable);

    Page<PostResponse> findAllRecommendedOfGame(Long gameId, PostStatus postStatus, Pageable pageable);

    Post findById(Long postId);

    Post findByIdAndUserId(Long postId, Long userId);

    void delete(Post deletePost);

    List<Tuple> findByPopularPost(int size, int page);

    List<PostResponse> findByRecentPost(int size, int page);
}
