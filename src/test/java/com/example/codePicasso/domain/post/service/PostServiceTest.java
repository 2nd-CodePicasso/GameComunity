package com.example.codePicasso.domain.post.service;

import com.example.codePicasso.domain.category.entity.Category;
import com.example.codePicasso.domain.category.service.CategoryConnector;
import com.example.codePicasso.domain.game.entity.Game;
import com.example.codePicasso.domain.game.service.GameConnector;
import com.example.codePicasso.domain.post.dto.request.PostRequest;
import com.example.codePicasso.domain.post.dto.response.PostListResponse;
import com.example.codePicasso.domain.post.dto.response.PostResponse;
import com.example.codePicasso.domain.post.entity.Post;
import com.example.codePicasso.domain.post.enums.PostStatus;
import com.example.codePicasso.domain.user.entity.Admin;
import com.example.codePicasso.domain.user.entity.User;
import com.example.codePicasso.domain.user.service.UserConnector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    private List<Post> posts;

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
                .viewCount(0)
                .status(PostStatus.RECOMMENDED)
                .build();
        posts = new ArrayList<>();
        posts.add(mockPost);
        postRequest = new PostRequest(1L, "testTitle", "This is a test post.");
    }

    @Test
    void 게시글_생성() {
        // Given
        Long userId = 1L;
        Long categoryId = 1L;
        PostRequest request = new PostRequest(1L, "testTitle", "This is a test post.");

        // When

        when(userConnector.findById(userId)).thenReturn(mockUser);
        when(categoriesConnector.findById(categoryId)).thenReturn(mockCategory);
        when(postConnector.save(any(Post.class))).thenReturn(mockPost);

        PostResponse response = postService.createPost(userId, request);

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
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        Page<Post> pagePosts = new PageImpl<>(posts, pageable, posts.size());

        //when
        when(postConnector.findAllByGameId(eq(gameId), any(Pageable.class))).thenReturn(pagePosts);
        PostListResponse postListResponse = postService.findAllByGameId(gameId, page, size);

        //then
        verify(postConnector).findAllByGameId(eq(gameId), eq(pageable));
        assertEquals(posts.get(0).getId(), postListResponse.postResponses().get(0).postId());
        assertEquals(posts.get(0).getGame().getId(), postListResponse.postResponses().get(0).gameId());
        assertEquals(posts.get(0).getTitle(), postListResponse.postResponses().get(0).title());
        assertEquals(posts.get(0).getCategory().getCategoryName(), postListResponse.postResponses().get(0).categoryName());
        assertEquals(posts.get(0).getDescription(), postListResponse.postResponses().get(0).description());
    }

    @Test
    void 게시물_찾기_카테고리아이디() {
        //given
        Long categoryId = 1L;
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        Page<Post> pagePosts = new PageImpl<>(posts, pageable, posts.size());

        //when
        when(postConnector.findAllByCategoryId(eq(categoryId), any(Pageable.class))).thenReturn(pagePosts);
        PostListResponse postListResponse = postService.findAllByCategoryId(categoryId, page, size);

        //then
        verify(postConnector).findAllByCategoryId(eq(categoryId), eq(pageable));
        assertEquals(posts.get(0).getId(), postListResponse.postResponses().get(0).postId());
        assertEquals(posts.get(0).getGame().getId(), postListResponse.postResponses().get(0).gameId());
        assertEquals(posts.get(0).getTitle(), postListResponse.postResponses().get(0).title());
        assertEquals(posts.get(0).getCategory().getCategoryName(), postListResponse.postResponses().get(0).categoryName());
        assertEquals(posts.get(0).getDescription(), postListResponse.postResponses().get(0).description());
    }

    @Test
    void 게시물_찾기_추천게시물() {
        // Given
        Long gameId = 1L;
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        Page<Post> pagePosts = new PageImpl<>(posts, pageable, posts.size());

        // When
        when(postConnector.findAllRecommendedOfGame(eq(gameId),eq(PostStatus.RECOMMENDED), any(Pageable.class))).thenReturn(pagePosts);
        PostListResponse postListResponse = postService.findRecommendedPost(gameId, page, size);

        // Then
        verify(postConnector).findAllRecommendedOfGame(eq(gameId),eq(PostStatus.RECOMMENDED), eq(pageable));
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
