package com.example.codePicasso.domain.category.service;

import com.example.codePicasso.domain.category.dto.request.CategoryRequest;
import com.example.codePicasso.domain.category.dto.response.CategoryListResponse;
import com.example.codePicasso.domain.category.dto.response.CategoryResponse;
import com.example.codePicasso.domain.category.entity.Category;
import com.example.codePicasso.domain.game.entity.Game;
import com.example.codePicasso.domain.game.service.GameConnector;
import com.example.codePicasso.domain.user.entity.Admin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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


    private Admin mockAdmin;
    private Game mockGame;
    private Category mockCategory;
    private CategoryRequest categoryRequest;
    private List<Category> categories = new ArrayList<>();

    @BeforeEach
    void setUp() {
        mockAdmin = new Admin("admin", "testAdmin");
        mockGame = Game.builder()
                .id(1L)
                .admin(mockAdmin)
                .gameTitle("testTitle")
                .gameDescription("This is a test game.")
                .build();
        mockCategory = Category.builder()
                .game(mockGame)
                .categoryName("test Category")
                .build();
        categories.add(mockCategory);
    }

    @Test
    void 카테고리_생성() {
        //given
        Long gameId = 1L;
        CategoryRequest request = new CategoryRequest("test Category");

        //when
        when(gameConnector.findByIdForUser(gameId)).thenReturn(mockGame);
        when(categoryConnector.save(any(Category.class))).thenReturn(mockCategory);

        CategoryResponse categoryResponse = categoryService.createCategory(gameId, request);

        //then
        verify(categoryConnector, times(1)).save(any());
        verify(gameConnector).findByIdForUser(gameId);

        assertEquals(mockGame.getId(), categoryResponse.gameId());
        assertEquals(request.categoryName(), categoryResponse.categoryName());
    }

    @Test
    void 모든카테고리_조회() {
        //given
        Long gameId = 1L;

        categories.clear();
        categories.add(mockCategory);

        //when
        when(categoryConnector.findCategoryByGameId(gameId)).thenReturn(categories);
        CategoryListResponse categoryListResponse = categoryService.getAllCategory(gameId);

        //then
        verify(categoryConnector).findCategoryByGameId(gameId);

        List<CategoryResponse> categoryList = categoryListResponse.categoryResponses();
        assertFalse(categoryList.isEmpty(), "Not found category List");

        CategoryResponse getCategory = categoryList.get(0);
        assertEquals(categories.get(0).getCategoryName(), getCategory.categoryName());
    }

    @Test
    void 카테고리_수정() {
        //given
        Long categoryId = 1L;
        CategoryRequest request = new CategoryRequest("Updated Category");

        //when
        when(categoryConnector.findById(categoryId)).thenReturn(mockCategory);

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
