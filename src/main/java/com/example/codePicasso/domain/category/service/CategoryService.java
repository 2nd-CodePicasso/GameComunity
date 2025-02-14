package com.example.codePicasso.domain.category.service;

import com.example.codePicasso.domain.category.dto.request.CategoryCreateRequest;
import com.example.codePicasso.domain.category.dto.response.CategoryResponse;
import com.example.codePicasso.domain.category.entity.Category;
import com.example.codePicasso.domain.game.entity.Game;
import com.example.codePicasso.domain.game.service.GameConnector;
import com.example.codePicasso.domain.users.entity.User;
import com.example.codePicasso.domain.users.service.UserConnector;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryConnector categoryConnector;
    private final UserConnector userConnector;
    private final GameConnector gameConnector;

    // Todo toEntity, save 는 각각 클래스에 만들어주실거쥬?
    // 파라미터에 userId, gameId만 있어서 Long 타입이라고 추가해뒀습니다
    public CategoryResponse createCategory(Long userId, Long  gameId, CategoryCreateRequest request) {
        User user = userConnector.findById(userId);
        Game game = gameConnector.findById(gameId);
        Category createCategory = request.toEntity(user, game);
        categoryConnector.save(createCategory);
        return createCategory.toDto();
    }

}
