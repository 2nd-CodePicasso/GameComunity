package com.example.codePicasso.domain.post.service;

import com.example.codePicasso.domain.category.entity.Category;
import com.example.codePicasso.domain.category.service.CategoryConnector;
import com.example.codePicasso.domain.post.dto.request.PostRequest;
import com.example.codePicasso.domain.post.dto.response.PostListResponse;
import com.example.codePicasso.domain.post.dto.response.PostResponse;
import com.example.codePicasso.domain.post.entity.Post;
import com.example.codePicasso.domain.post.enums.PostStatus;
import com.example.codePicasso.domain.user.entity.User;
import com.example.codePicasso.domain.user.service.UserConnector;
import com.example.codePicasso.global.common.DtoFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    // 게시글 생성
    @Transactional
    public PostResponse createPost(Long userId, PostRequest request) {
        User user = userConnector.findById(userId);
        Category category = categoryConnector.findById(request.categoryId());
        Post createPost = request.toEntity(user, category.getGame(), category);
        Post save = postConnector.save(createPost);
        return DtoFactory.toPostDto(save);
    }

    // 게시물 조회(gameId)
    public PostListResponse findAllByGameId(Long gameId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postResponses = postConnector.findAllByGameId(gameId, pageable);
        return DtoFactory.toPaginationPostDto(postResponses);
    }

    // 게시물 조회(categoryId)
    public PostListResponse findAllByCategoryId(Long categoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postResponses = postConnector.findAllByCategoryId(categoryId, pageable);
        return DtoFactory.toPaginationPostDto(postResponses);
    }

    // 게시물 조회(추천게시물)
    public PostListResponse findRecommendedPost(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postResponses = postConnector.findAllByStatus(PostStatus.RECOMMENDED, pageable);
        return DtoFactory.toPaginationPostDto(postResponses);
    }

    // 게시글 개별 조회
    public PostResponse findById(Long postId) {
        Post getPost = postConnector.findById(postId);
          getPost.increaseViewCount();
        return DtoFactory.toPostDto(getPost);
    }

    // 게시글 수정
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

    // 게시글 삭제
    @Transactional
    public void deletePost(Long postId, Long userId) {
        Post deletePost = postConnector.findByIdAndUserId(postId, userId);

        postConnector.delete(deletePost);
    }
}
