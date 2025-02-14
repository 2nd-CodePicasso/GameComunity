package com.example.codePicasso.domain.post.service;

import com.example.codePicasso.domain.category.entity.Category;
import com.example.codePicasso.domain.category.service.CategoryConnector;
import com.example.codePicasso.domain.game.entity.Game;
import com.example.codePicasso.domain.game.service.GameConnector;
import com.example.codePicasso.domain.post.dto.request.PostCreateRequest;
import com.example.codePicasso.domain.post.dto.request.PostUpdateRequest;
import com.example.codePicasso.domain.post.dto.response.GetGameIdAllPostsResponse;
import com.example.codePicasso.domain.post.dto.response.PostResponse;
import com.example.codePicasso.domain.post.entity.Post;
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
        Category category = categoryConnector.findById(request.categoryId());
        Post createPost = request.toEntity(user, game, category);
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
    public PostResponse updatePost(Long postId, PostUpdateRequest request, Long userId) {
        Post foundPost = postConnector.findByUserIdAndPostId(postId, userId)
                .orElseThrow(() -> new InvalidRequestException(ErrorCode.POST_NOT_FOUND));

        if (!foundPost.getCategory().getId().equals(request.categoryId())) {
            Category category = categoryConnector.findById(request.categoryId());
            foundPost.updateCategories(category);
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
