package com.example.codePicasso.domain.category.service;

import com.example.codePicasso.domain.category.dto.request.CategoryCreateRequest;
import com.example.codePicasso.domain.category.dto.response.CategoryResponse;
import com.example.codePicasso.domain.category.entity.Category;
import com.example.codePicasso.domain.game.entity.Game;
import com.example.codePicasso.domain.game.service.GameConnector;
import com.example.codePicasso.domain.post.dto.response.GetAllCategoryByGameIdResponse;
import com.example.codePicasso.domain.post.service.PostConnector;
import com.example.codePicasso.domain.users.entity.User;
import com.example.codePicasso.domain.users.service.UserConnector;
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
    public CategoryResponse createCategory(Long gameId, CategoryCreateRequest request) {
        Game game = gameConnector.findById(gameId);
        Category createCategory = request.toEntity(game);
        categoryConnector.save(createCategory);
        return createCategory.toDto();
    }

    public List<GetAllCategoryByGameIdResponse> getAllCategory(Long gameId) {
        List<GetAllCategoryByGameIdResponse> findCategoryByGameId = categoryConnector.findCategoryByGameId(gameId);
        return findCategoryByGameId;
    }
}
