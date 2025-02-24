package com.example.codePicasso.domain.post.service;

import com.example.codePicasso.domain.category.entity.Category;
import com.example.codePicasso.domain.category.service.CategoryConnector;
import com.example.codePicasso.domain.game.entity.Game;
import com.example.codePicasso.domain.game.service.GameConnector;
import com.example.codePicasso.domain.post.dto.request.PostRequest;
import com.example.codePicasso.domain.post.dto.response.PostListResponse;
import com.example.codePicasso.domain.post.dto.response.PostResponse;
import com.example.codePicasso.domain.post.entity.Post;
import com.example.codePicasso.domain.user.entity.Admin;
import com.example.codePicasso.domain.user.entity.User;
import com.example.codePicasso.domain.user.service.UserConnector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostConnector postConnector;

    @Mock
    private GameConnector gameConnector;

    @Mock
    private UserConnector userConnector;

    @Mock
    private CategoryConnector categoriesConnector;

    private Post mockPost;
    private User mockUser;
    private Admin mockAdmin;
    private Game mockGame;
    private Category mockCategory;
    private PostRequest postRequest;
    private List<Post> posts = new ArrayList<>();

    @BeforeEach
    void setUp() {
        mockUser = new User("user", "testUser", "user123");  // 예시: User 객체 생성
        mockAdmin = new Admin("admin", "testAdmin");
        mockGame = Game.builder()
                .id(1L)
                .admin(mockAdmin)
                .gameTitle("testTitle")
                .gameDescription("This is a test game.")
                .build();
        mockCategory = Category.builder()
                .id(1L)
                .game(mockGame)
                .categoryName("test Category")
                .build();
        mockPost = Post.builder()
                .user(mockUser)
                .game(mockGame)
                .category(mockCategory)
                .title("test Title")
                .description("This is a test post.")
                .build();
        posts.add(mockPost);
        postRequest = new PostRequest(1L, "testTitle", "This is a test post.");
    }

    @Test
    void 깡통_테스트1() {

    }

    @Test
    void 깡통_테스트2() {

    }

    @Test
    void 게시글_생성() {
        // Given
        Long userId = 1L;
        Long categoryId = 1L;
//        PostRequest request = new PostRequest(1L, "testTitle", "This is a test post.");

        // When

        when(userConnector.findById(userId)).thenReturn(mockUser);
        when(categoriesConnector.findById(categoryId)).thenReturn(mockCategory);
        when(postConnector.save(any(Post.class))).thenReturn(mockPost);

        PostResponse response = postService.createPost(userId, postRequest);

        // Then
        verify(postConnector, times(1)).save(any());
        verify(userConnector).findById(userId);
        verify(categoriesConnector).findById(categoryId);
        assertEquals(mockGame.getId(), response.gameId());
        assertEquals(mockCategory.getCategoryName(), response.categoryName());
        assertEquals(mockPost.getTitle(), response.title());
        assertEquals(mockPost.getDescription(), response.description());
    }

    @Test
    void 게시물_찾기_게임아이디() {
        //given
        Long gameId = 1L;

        //when

        when(postConnector.findAllByGameId(gameId)).thenReturn(posts);
        PostListResponse postListResponse = postService.findAllByGameId(gameId);

        //then
        verify(postConnector).findAllByGameId(gameId);
        assertEquals(posts.get(0).getId(),postListResponse.postResponses().get(0).postId());
        assertEquals(posts.get(0).getGame().getId(),postListResponse.postResponses().get(0).gameId());
        assertEquals(posts.get(0).getTitle(),postListResponse.postResponses().get(0).title());
        assertEquals(posts.get(0).getCategory().getCategoryName(),postListResponse.postResponses().get(0).categoryName());
        assertEquals(posts.get(0).getDescription(),postListResponse.postResponses().get(0).description());
//        verify(postConnector).findAllByGameId(gameId);
//        assertEquals(posts.get(0).getId(), postResponses.get(0).postId());
//        assertEquals(posts.get(0).getGame().getId(), postResponses.get(0).gameId());
//        assertEquals(posts.get(0).getTitle(), postResponses.get(0).title());
//        assertEquals(posts.get(0).getCategory().getCategoryName(), postResponses.get(0).categoryName());
//        assertEquals(posts.get(0).getDescription(), postResponses.get(0).description());
    }

    @Test
    void 게시물_찾기_카테고리아이디() {
        //given
        Long categoryId = 1L;

        //when
        when(postConnector.findAllByCategoryId(categoryId)).thenReturn(posts);
        PostListResponse postListResponse = postService.findAllByCategoryId(categoryId);

        //then
        verify(postConnector).findAllByCategoryId(categoryId);
        assertEquals(posts.get(0).getId(), postListResponse.postResponses().get(0).postId());
        assertEquals(posts.get(0).getGame().getId(), postListResponse.postResponses().get(0).gameId());
        assertEquals(posts.get(0).getTitle(), postListResponse.postResponses().get(0).title());
        assertEquals(posts.get(0).getCategory().getCategoryName(), postListResponse.postResponses().get(0).categoryName());
        assertEquals(posts.get(0).getDescription(), postListResponse.postResponses().get(0).description());
    }

    @Test
    void 게시물_찾기_게시물아이디() {
        //given
        Long postId = 1L;
        //when

        when(postConnector.findById(postId)).thenReturn(mockPost);
        PostResponse postResponse = postService.findById(postId);

        //then
        verify(postConnector).findById(postId);
        assertEquals(mockPost.getId(), postResponse.postId());
        assertEquals(mockPost.getGame().getId(), postResponse.gameId());
        assertEquals(mockPost.getTitle(), postResponse.title());
        assertEquals(mockPost.getCategory().getCategoryName(), postResponse.categoryName());
        assertEquals(mockPost.getDescription(), postResponse.description());
    }

    @Test
    void 게시물_수정() {
        //given
        Long postId = 1L;
        Long userId = 1L;
        Long categoryId = 2L;

        PostRequest updateRequest = new PostRequest(2L, "testTitle", "This is a test post.");

        //when
        when(postConnector.findByIdAndUserId(postId, userId)).thenReturn(mockPost);
        when(categoriesConnector.findById(categoryId)).thenReturn(mockCategory);

        PostResponse postResponse = postService.updatePost(postId, userId, updateRequest);

        //then
        verify(postConnector).findByIdAndUserId(postId, userId);
        verify(categoriesConnector).findById(2L);
        assertEquals(postRequest.title(), postResponse.title());
        assertEquals(postRequest.description(), postResponse.description());
        assertEquals(mockCategory.getCategoryName(), postResponse.categoryName());
    }

    @Test
    void 게시물_삭제() {
        //given
        Long postId = 1L;
        Long userId = 1L;

        //when
        when(postConnector.findByIdAndUserId(postId, userId)).thenReturn(mockPost);
        postService.deletePost(postId, userId);

        //then
        verify(postConnector).findByIdAndUserId(postId, userId);
        verify(postConnector).delete(mockPost);

    }
}
