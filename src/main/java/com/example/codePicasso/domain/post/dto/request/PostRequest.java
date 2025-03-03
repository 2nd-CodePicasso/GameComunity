package com.example.codePicasso.domain.post.dto.request;

import com.example.codePicasso.domain.category.entity.Category;
import com.example.codePicasso.domain.game.entity.Game;
import com.example.codePicasso.domain.post.entity.Post;
import com.example.codePicasso.domain.post.enums.PostStatus;
import com.example.codePicasso.domain.user.entity.User;
import jakarta.validation.constraints.NotBlank;

public record PostRequest(
        Long categoryId,
        @NotBlank
        String title,
        @NotBlank
        String description
) {
    public Post toEntity(User user, Game game, Category category) {
        return Post.builder()
                .user(user)
                .game(game)
                .title(title)
                .description(description)
                .viewCount(0)
                .status(PostStatus.NORMAL)
                .category(category)
                .build();
    }
}