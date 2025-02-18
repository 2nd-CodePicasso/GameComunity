package com.example.codePicasso.domain.post.repository;

import com.example.codePicasso.domain.post.dto.response.PostResponse;
import com.example.codePicasso.domain.post.entity.Post;
import com.example.codePicasso.domain.post.service.PostConnector;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PostConnectorImpl implements PostConnector {
    private final PostRepository postRepository;

    // 게시글 생성
    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    // gameId로 게시글 전체 조회
    @Override
    public List<PostResponse> findPostByGameId(Long gameId) {
        return postRepository.findPostByGameId(gameId);
    }

    // categoryId로 게시글 전체 조회
    @Override
    public List<Post> findPostByCategoryId(Long gameId, Long categoryId) {
        return postRepository.findPostByGameIdAndCategoryId(gameId, categoryId);
    }

    // 게시글 개별 조회
    @Override
    public Optional<Post> findByPostId(Long gameId, Long postId) {
        return postRepository.findByGameIdAndPostId(gameId, postId);
    }

    // 게시글 수정
    @Override
    public Optional<Post> findByUserIdAndPostId(Long gameId, Long postId, Long userId) {
        return postRepository.findByUserIdAndPostId(gameId, postId, userId);
    }

    // 게시글 삭제
    @Override
    public void delete(Post deletePost) {
        postRepository.delete(deletePost);
    }
}
