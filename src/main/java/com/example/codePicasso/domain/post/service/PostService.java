package com.example.codePicasso.domain.post.service;

import com.example.codePicasso.domain.category.entity.Category;
import com.example.codePicasso.domain.category.service.CategoryConnector;
import com.example.codePicasso.domain.game.entity.Game;
import com.example.codePicasso.domain.game.service.GameConnector;
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
@Transactional(readOnly = true)
public class PostService {

    private final PostConnector postConnector;
    private final GameConnector gameConnector;
    private final CategoryConnector categoryConnector;
    private final UserConnector userConnector;

    @Transactional
    public PostResponse createPost(Long userId, Long gameId, Long categoryId, String title, String description) {
        Game game = gameConnector.findById(gameId);
        User user = userConnector.findById(userId);
        Category category = categoryConnector.findById(categoryId);
        Post createPost = Post.toEntity(user, game, category, title, description);
        postConnector.save(createPost);
        return PostResponse.toDto(createPost);
    }

    public List<GetGameIdAllPostsResponse> findPostByGameId(Long gameId) {
        return postConnector.findPostByGameId(gameId);
    }

    public List<PostResponse> findPostByCategoryId(Long categoryId) {
        List<Post> findPostsByCategoryId = postConnector.findPostByCategoryId(categoryId);
        return findPostsByCategoryId.stream()
                .map(PostResponse::toDto).toList();
    }

    @Transactional
    public PostResponse findPostById(Long postId) {
        Post getPost = postConnector.findById(postId)
                .orElseThrow(() -> new InvalidRequestException(ErrorCode.POST_NOT_FOUND));
        return PostResponse.toDto(getPost);
    }

    @Transactional
    public PostResponse updatePost(Long postId, Long userId, Long categoryId, String title, String description ) {
        Post foundPost = postConnector.findByUserIdAndPostId(postId, userId)
                .orElseThrow(() -> new InvalidRequestException(ErrorCode.POST_NOT_FOUND));

        if (!foundPost.getCategory().getId().equals(categoryId)) {
            Category category = categoryConnector.findById(categoryId);
            foundPost.updateCategories(category);
        }

        foundPost.updatePost(title, description);

        return PostResponse.toDto(foundPost);
    }

    @Transactional
    public void deletePost(Long postId, Long userId) {
        Post deletePost = postConnector.findByUserIdAndPostId(postId, userId)
                .orElseThrow(() -> new InvalidRequestException(ErrorCode.POST_NOT_FOUND));

        postConnector.delete(deletePost);
    }
}
