package com.example.codePicasso.domain.post.service;

import com.example.codePicasso.domain.post.dto.response.GetGameIdAllPostsResponse;
import com.example.codePicasso.domain.post.entity.Post;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface PostConnector {

    Post save(Post post);

    List<GetGameIdAllPostsResponse> findPostByGameId(Long gameId);

    List<Post> findPostByCategoryId(Long categoryId);

    Post findById(Long postId);

    Post findByUserIdAndPostId(Long postId, Long userId);

    void delete(Post deletePost);


}
