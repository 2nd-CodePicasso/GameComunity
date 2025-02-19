package com.example.codePicasso.domain.post.service;

import com.example.codePicasso.domain.category.entity.Category;
import com.example.codePicasso.domain.category.service.CategoryConnector;
import com.example.codePicasso.domain.game.entity.Game;
import com.example.codePicasso.domain.game.service.GameConnector;
import com.example.codePicasso.domain.post.dto.request.PostRequest;
import com.example.codePicasso.domain.post.dto.response.GetGameIdAllPostsResponse;
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
    private List<Post> posts;

    @BeforeEach
    void setUp() {
        mockUser = new User("user", "testUser","user123");  // 예시: User 객체 생성
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
        Long gameId = 1L;
        Long categoryId = 1L;
        PostRequest request = new PostRequest(1L, "testTitle", "This is a test post.");

        // When

        when(gameConnector.findById(gameId)).thenReturn(mockGame);
        when(userConnector.findById(userId)).thenReturn(mockUser);
        when(categoriesConnector.findById(categoryId)).thenReturn(mockCategory);

        PostResponse response = postService.createPost(userId, gameId, categoryId, request.title(), request.description());

        // Then
        verify(postConnector, times(1)).save(any());
        verify(gameConnector).findById(gameId);
        verify(userConnector).findById(userId);
        verify(categoriesConnector).findById(categoryId);
        assertEquals(mockGame.getId(),response.gameId());
        assertEquals(mockCategory.getCategoryName(), response.categoryName());
        assertEquals(request.title(), response.title());
        assertEquals(request.description(), response.description());
    }

    @Test
    void 게시물_찾기_게임아이디() {
        //given
        Long gameId = 1L;

        //when

    //    when(postConnector.findPostByGameId(gameId)).thenReturn()

    }
}
