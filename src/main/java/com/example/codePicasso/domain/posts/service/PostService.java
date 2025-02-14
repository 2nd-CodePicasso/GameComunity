package com.example.codePicasso.domain.posts.service;

import com.example.codePicasso.domain.game.entity.Game;
import com.example.codePicasso.domain.game.service.GameConnector;
import com.example.codePicasso.domain.posts.dto.request.PostCreateRequest;
import com.example.codePicasso.domain.posts.dto.request.UpdateRequest;
import com.example.codePicasso.domain.posts.dto.response.GetGameIdAllPostsResponse;
import com.example.codePicasso.domain.posts.dto.response.PostResponse;
import com.example.codePicasso.domain.posts.entity.Categories;
import com.example.codePicasso.domain.posts.entity.Post;
import com.example.codePicasso.domain.users.entity.User;
import com.example.codePicasso.domain.users.service.UserConnector;
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
public class PostService {

    private final PostConnector postConnector;
    private final GameConnector gameConnector;
    private final CategoriesConnector categoriesConnector;
    private final UserConnector userConnector;

    @Transactional
    public PostResponse createPost(Long userId, Long gameId, PostCreateRequest request) {
        Game game = gameConnector.findById(gameId);
        User user = userConnector.findById(userId);
        Categories categories = categoriesConnector.findById(request.categoryId());
        Post createPost = request.toEntity(user, game, categories);
        postConnector.save(createPost);
        return createPost.toDto();
    }

    @Transactional(readOnly = true)
    public List<GetGameIdAllPostsResponse> findPostByGameId(Long gameId) {
        List<GetGameIdAllPostsResponse> findpostsByGameId = postConnector.findPostByGameId(gameId);
        return findpostsByGameId;
    }

    @Transactional(readOnly = true)
    public List<PostResponse> findPostByCategoryId(Long categoryId) {
        List<Post> findPostsByCategoryId = postConnector.findPostByCategoryId(categoryId);
        return findPostsByCategoryId.stream()
                .map(Post::toDto).toList();
    }

    @Transactional
    public PostResponse findById(Long postId) {
        Post getPost = postConnector.findById(postId).
                orElseThrow(() -> new InvalidRequestException(ErrorCode.POST_NOT_FOUND));
        return getPost.toDto();
    }

    @Transactional
    public PostResponse updatePost(Long postId, UpdateRequest request, Long userId) {
        Post foundPost = postConnector.findByUserIdAndPostId(postId, userId)
                .orElseThrow(() -> new InvalidRequestException(ErrorCode.POST_NOT_FOUND));

        if (!foundPost.getCategories().getId().equals(request.categoryId())) {
            Categories categories = categoriesConnector.findById(request.categoryId());
            foundPost.updateCategories(categories);
        }

        foundPost.updatePost(request.title(), request.description());

        return foundPost.toDto();
    }

    @Transactional
    public void deletePost(Long postId, Long userId) {
        Post deletePost = postConnector.findByUserIdAndPostId(postId, userId)
                .orElseThrow(() -> new InvalidRequestException(ErrorCode.POST_NOT_FOUND));

        postConnector.delete(deletePost);
    }
}
