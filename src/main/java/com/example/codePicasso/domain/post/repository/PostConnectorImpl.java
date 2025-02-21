package com.example.codePicasso.domain.post.repository;

import com.example.codePicasso.domain.post.entity.Post;
import com.example.codePicasso.domain.post.service.PostConnector;
import com.example.codePicasso.global.exception.base.InvalidRequestException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

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
    public List<Post> findAllByGameId(Long gameId) {
        return postRepository.findAllByGameId(gameId);
    }

    // categoryId로 게시글 전체 조회
    @Override
    public List<Post> findAllByCategoryId(Long categoryId) {
        return postRepository.findAllByCategoryId(categoryId);
    }

    // 게시글 개별 조회
    @Override
    public Post findById(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new InvalidRequestException(ErrorCode.POST_NOT_FOUND));
    }

    // 게시글 수정
    @Override
    public Post findByIdAndUserId(Long postId, Long userId) {
        return postRepository.findByIdAndUserId(postId, userId).orElseThrow(() -> new InvalidRequestException(ErrorCode.POST_NOT_FOUND));
    }

    // 게시글 삭제
    @Override
    public void delete(Post deletePost) {
        postRepository.delete(deletePost);
    }
}
