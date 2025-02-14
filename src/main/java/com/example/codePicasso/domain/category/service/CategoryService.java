package com.example.codePicasso.domain.category.service;

import com.example.codePicasso.domain.category.dto.response.CategoryResponse;
import com.example.codePicasso.domain.category.entity.Category;
import com.example.codePicasso.domain.game.entity.Game;
import com.example.codePicasso.domain.game.service.GameConnector;
import com.example.codePicasso.domain.post.dto.response.GetAllCategoryByGameIdResponse;
import com.example.codePicasso.domain.post.service.PostConnector;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryConnector categoryConnector;
    private final GameConnector gameConnector;
    private final PostConnector postConnector;

    // 파라미터에 userId, gameId만 있어서 Long 타입이라고 추가해뒀습니다
    public CategoryResponse createCategory(Long gameId, String categoryName) {
        Game game = gameConnector.findById(gameId);
        Category createCategory = Category.toEntity(game, categoryName);
        categoryConnector.save(createCategory);
        return CategoryResponse.toDto(createCategory);
    }

    public List<GetAllCategoryByGameIdResponse> getAllCategory(Long gameId) {
        return categoryConnector.findCategoryByGameId(gameId).stream()
                .map(GetAllCategoryByGameIdResponse::toDto).toList();
    }

}
