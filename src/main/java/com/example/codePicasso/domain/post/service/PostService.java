package com.example.codePicasso.domain.post.service;

import com.example.codePicasso.domain.category.entity.Category;
import com.example.codePicasso.domain.category.service.CategoryConnector;
import com.example.codePicasso.domain.game.entity.Game;
import com.example.codePicasso.domain.game.service.GameConnector;
import com.example.codePicasso.domain.post.dto.response.PostResponse;
import com.example.codePicasso.domain.post.entity.Post;
import com.example.codePicasso.domain.user.entity.User;
import com.example.codePicasso.domain.user.service.UserConnector;
import com.example.codePicasso.global.exception.base.InvalidRequestException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostConnector postConnector;
    private final GameConnector gameConnector;
    private final CategoryConnector categoryConnector;
    private final UserConnector userConnector;

    // 게시글 생성
    @Transactional
    public PostResponse createPost(Long userId, Long categoryId, String title, String description) {
        User user = userConnector.findById(userId);
        Category category = categoryConnector.findById(categoryId);
        Post createPost = Post.toEntity(user, category.getGame(), category, title, description);
        postConnector.save(createPost);
        return PostResponse.toDto(createPost);
    }

    // gameId로 게시글 전체 조회
    public List<PostResponse> findAllByGameId(Long gameId) {
        return postConnector.findAllByGameId(gameId).stream()
                .map(PostResponse::toDto).toList();
    }

    // categoryId로 게시글 전체 조회
    public List<PostResponse> findAllByCategoryId(Long categoryId) {
        return postConnector.findAllByCategoryId(categoryId).stream()
                .map(PostResponse::toDto).toList();
    }

    // 게시글 개별 조회
    public PostResponse findById(Long postId) {
        Post getPost = postConnector.findById(postId);
        return PostResponse.toDto(getPost);
    }

    // 게시글 수정
    @Transactional
    public PostResponse updatePost(Long postId, Long userId, Long categoryId, String title, String description) {
        Post foundPost = postConnector.findByIdAndUserId(postId, userId);

        if (!foundPost.getCategory().getId().equals(categoryId)) {
            Category category = categoryConnector.findById(categoryId);
            foundPost.updateCategories(category);
        }

        foundPost.updatePost(title, description);

        return PostResponse.toDto(foundPost);
    }

    // 게시글 삭제
    @Transactional
    public void deletePost(Long postId, Long userId) {
        Post deletePost = postConnector.findByIdAndUserId(postId, userId);

        postConnector.delete(deletePost);
    }
}
