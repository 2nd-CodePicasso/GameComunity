package com.example.codePicasso.domain.post.service;

import com.example.codePicasso.domain.category.entity.Category;
import com.example.codePicasso.domain.category.service.CategoryConnector;
import com.example.codePicasso.domain.game.entity.Game;
import com.example.codePicasso.domain.game.service.GameConnector;
import com.example.codePicasso.domain.post.dto.request.PostRequest;
import com.example.codePicasso.domain.post.dto.response.PostResponse;
import com.example.codePicasso.domain.post.entity.Post;
import com.example.codePicasso.domain.user.entity.User;
import com.example.codePicasso.domain.user.service.UserConnector;
import com.example.codePicasso.global.common.DtoFactory;
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
    public PostResponse createPost(Long userId, Long gameId, PostRequest postRequest) {
        Game game = gameConnector.findById(gameId);
        User user = userConnector.findById(userId);
        Category category = categoryConnector.findById(postRequest.categoryId());
        Post createPost = postRequest.toEntity(user, game,category);
        Post save = postConnector.save(createPost);
        return DtoFactory.toPostDto(save);
    }

    public List<PostResponse> findPostByGameId(Long gameId) {
        return postConnector.findPostByGameId(gameId).stream()
                .map(DtoFactory::toPostDto).toList();
    }

    public List<PostResponse> findPostByCategoryId(Long categoryId) {
        return postConnector.findPostByCategoryId(categoryId).stream()
                .map(DtoFactory::toPostDto).toList();
    }

    // 게시글 개별 조회
    public PostResponse findById(Long postId) {
        Post getPost = postConnector.findById(postId);
        return DtoFactory.toPostDto(getPost);
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

        return DtoFactory.toPostDto(foundPost);
    }

    // 게시글 삭제
    @Transactional
    public void deletePost(Long postId, Long userId) {
        Post deletePost = postConnector.findByIdAndUserId(postId, userId);

        postConnector.delete(deletePost);
    }
}
