package com.example.codePicasso.domain.post.service;

import com.example.codePicasso.domain.category.entity.Category;
import com.example.codePicasso.domain.category.service.CategoryConnector;
import com.example.codePicasso.domain.game.entity.Game;
import com.example.codePicasso.domain.game.service.GameConnector;
import com.example.codePicasso.domain.post.dto.request.PostRequest;
import com.example.codePicasso.domain.post.dto.response.PostListResponse;
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
    private final CategoryConnector categoryConnector;
    private final UserConnector userConnector;

    @Transactional
    public PostResponse createPost(Long userId, PostRequest request) {
        User user = userConnector.findById(userId);
        Category category = categoryConnector.findById(request.categoryId());
        Post createPost = request.toEntity(user, category.getGame(), category);
        Post save = postConnector.save(createPost);
        return DtoFactory.toPostDto(save);
    }

    public PostListResponse findPostByGameId(Long gameId) {
        List<PostResponse> postResponses = postConnector.findPostByGameId(gameId).stream()
                .map(DtoFactory::toPostDto).toList();
        return PostListResponse.builder()
                .postResponses(postResponses)
                .build();
    }

    public PostListResponse findPostByCategoryId(Long categoryId) {
        List<PostResponse> postResponses = postConnector.findPostByCategoryId(categoryId).stream()
                .map(DtoFactory::toPostDto).toList();
        return PostListResponse.builder()
                .postResponses(postResponses)
                .build();
    }

    public PostResponse findPostById(Long postId) {
        Post getPost = postConnector.findById(postId);
        return DtoFactory.toPostDto(getPost);
    }

    @Transactional
    public PostResponse updatePost(Long postId, Long userId, PostRequest postRequest) {
        Post foundPost = postConnector.findByIdAndUserId(postId, userId);

        if (!foundPost.getCategory().getId().equals(postRequest.categoryId())) {
            Category category = categoryConnector.findById(postRequest.categoryId());
            foundPost.updateCategories(category);
        }

        foundPost.updatePost(postRequest.title(), postRequest.description());

        return DtoFactory.toPostDto(foundPost);
    }

    @Transactional
    public void deletePost(Long postId, Long userId) {
        Post deletePost = postConnector.findByIdAndUserId(postId, userId);

        postConnector.delete(deletePost);
    }
}
