package com.example.codePicasso.domain.category.service;

import com.example.codePicasso.domain.category.dto.request.CategoryRequest;
import com.example.codePicasso.domain.category.dto.response.CategoryListResponse;
import com.example.codePicasso.domain.category.dto.response.CategoryResponse;
import com.example.codePicasso.domain.category.entity.Category;
import com.example.codePicasso.domain.game.entity.Game;
import com.example.codePicasso.domain.game.service.GameConnector;
import com.example.codePicasso.domain.post.entity.Post;
import com.example.codePicasso.domain.user.entity.Admin;
import com.example.codePicasso.domain.user.entity.User;
import com.example.codePicasso.global.common.DtoFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryConnector categoryConnector;

    @Mock
    private GameConnector gameConnector;


    private Post mockPost;
    private User mockUser;
    private Admin mockAdmin;
    private Game mockGame;
    private Category mockCategory;
    private CategoryRequest categoryRequest;
    private List<Category> categories = new ArrayList<>();

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
        categories.add(mockCategory);
        categoryRequest = new CategoryRequest("category name");
    }

    @Test
    void 카테고리_생성() {
        //given
        Long gameId = 1L;
        CategoryRequest request = new CategoryRequest("Test Category");
        Category savedCategory = Category.builder()
                .id(1L)
                .game(mockGame)
                .categoryName("Test Category")
                .build();
        CategoryResponse expectedResponse = new CategoryResponse(1L, "Test Category");

        //when
        when(gameConnector.findById(gameId)).thenReturn(mockGame);
        when(categoryConnector.save(any(Category.class))).thenReturn(savedCategory);
        mockStatic(DtoFactory.class);
        when(DtoFactory.toCategoryDto(savedCategory)).thenReturn(expectedResponse);

        CategoryResponse categoryResponse = categoryService.createCategory(gameId, request);

        //then
        verify(gameConnector).findById(gameId);
        verify(categoryConnector).save(any(Category.class));
        assertEquals(request.categoryName(), categoryResponse.categoryName());
    }

    @Test
    void 모든카테고리_조회() {
        //given
        Long gameId = 1L;

        //when
        when(categoryConnector.findCategoryByGameId(gameId)).thenReturn(categories);
        CategoryListResponse categoryListResponse = categoryService.getAllCategory(gameId);
        List<CategoryResponse> categoryResponseList = categoryListResponse.categoryResponses();

        //then
        verify(categoryConnector).findCategoryByGameId(gameId);
        assertEquals(categories.get(0).getCategoryName(), categoryResponseList.get(0).categoryName());
    }

    @Test
    void 카테고리_수정() {
        //given
        Long categoryId = 1L;
        CategoryRequest request = new CategoryRequest("Updated Category");
        Category updatedCategory = Category.builder()
                .id(1L)
                .game(mockGame)
                .categoryName("Updated Category")
                .build();
        CategoryResponse expectedResponse = new CategoryResponse(1L, "Updated Category");

        //when
        when(categoryConnector.findById(categoryId)).thenReturn(mockCategory);
        mockCategory.updateCategory(request.categoryName());
        when(DtoFactory.toCategoryDto(mockCategory)).thenReturn(expectedResponse);

        CategoryResponse categoryResponse = categoryService.updateCategory(categoryId, request);

        //then
        verify(categoryConnector).findById(categoryId);
        assertEquals(request.categoryName(),categoryResponse.categoryName());
    }

    @Test
    void 카테고리_삭제() {
        //given
        Long categoryId = 1L;

        //when
        when(categoryConnector.findById(categoryId)).thenReturn(mockCategory);
        categoryService.deleteCategory(categoryId);

        //then
        verify(categoryConnector).findById(categoryId);
        verify(categoryConnector).delete(any(Category.class));

    }

}
