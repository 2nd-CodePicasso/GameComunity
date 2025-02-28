package com.example.codePicasso.domain.post.repository;

import com.example.codePicasso.domain.post.entity.Post;
import com.example.codePicasso.domain.post.enums.PostStatus;
import com.example.codePicasso.domain.post.service.PostConnector;
import com.example.codePicasso.global.exception.base.InvalidRequestException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

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
    public Page<Post> findAllByGameId(Long gameId, Pageable pageable) {
        return postRepository.findAllByGameId(gameId, pageable);
    }

    // 게임별 추천게시물 조회
    @Override
    public Page<Post> findAllRecommendedOfGame(Long gameId, PostStatus postStatus, Pageable pageable) {
        return postRepository.findAllByGameIdAndStatus(gameId, postStatus, pageable);
    }

    // categoryId로 게시글 전체 조회
    @Override
    public Page<Post> findAllByCategoryId(Long categoryId, Pageable pageable) {
        return postRepository.findAllByCategoryId(categoryId, pageable);
    }

    // 게시글 개별 조회
    @Override
    public Post findById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new InvalidRequestException(ErrorCode.POST_NOT_FOUND));
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
