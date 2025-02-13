package com.example.codePicasso.domain.posts.repository;

import com.example.codePicasso.domain.posts.entity.Post;
import com.example.codePicasso.domain.posts.service.PostConnector;
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
    public List<Post> findPostByGameId(Long gameId) {
        return postRepository.findPostByGameId(gameId);
    }

    @Override
    public List<Post> findPostByCategoryId(Long categoryId) {
        return postRepository.findPostByCategoryId(categoryId);
    }

    @Override
    public Optional<Post> findByUserIdAndPostId(Long postId, Long userId) {
        return postRepository.findByUserIdAndPostId(postId, userId);
    }

    @Override
    public void delete(Post deletePost) {
        postRepository.delete(deletePost);
    }
}
