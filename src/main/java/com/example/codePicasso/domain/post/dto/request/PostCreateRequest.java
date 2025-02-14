package com.example.codePicasso.domain.post.dto.request;


import com.example.codePicasso.domain.category.entity.Category;
import com.example.codePicasso.domain.game.entity.Game;
import com.example.codePicasso.domain.post.entity.Post;
import com.example.codePicasso.domain.users.entity.User;

public record PostCreateRequest(
        Long categoryId,
        String title,
        String description
) {
    public Post toEntity(User user, Game game, Category category) {
        return Post.builder()
                .user(user)
                .game(game)
                .category(category)
                .title(title)
                .description(description)
                .build();

    }
}

