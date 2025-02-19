package com.example.codePicasso.domain.post.repository;

import com.example.codePicasso.domain.post.dto.response.GetGameIdAllPostsResponse;
import com.example.codePicasso.domain.post.entity.Post;
import com.example.codePicasso.domain.post.service.PostConnector;
import com.example.codePicasso.global.exception.base.InvalidRequestException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PostConnectorImpl implements PostConnector {

    private final PostRepository postRepository;

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public List<GetGameIdAllPostsResponse> findPostByGameId(Long gameId) {
        return postRepository.findPostByGameId(gameId);
    }

    @Override
    public List<Post> findPostByCategoryId(Long categoryId) {
        return postRepository.findPostByCategoryId(categoryId);
    }

    @Override
    public Post findById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new InvalidRequestException(ErrorCode.POST_NOT_FOUND));
    }

    @Override
    public Post findByUserIdAndPostId(Long postId, Long userId) {
        return postRepository.findByUserIdAndPostId(postId, userId).orElseThrow(() -> new InvalidRequestException(ErrorCode.POST_NOT_FOUND));
    }

    @Override
    public void delete(Post deletePost) {
        postRepository.delete(deletePost);
    }
}
