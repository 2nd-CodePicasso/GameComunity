package com.example.codePicasso.domain.posts.dto.request;


import com.example.codePicasso.domain.games.entity.Games;
import com.example.codePicasso.domain.posts.entity.Categories;
import com.example.codePicasso.domain.posts.entity.Post;
import com.example.codePicasso.domain.users.entity.User;

public record PostCreateRequest(
        Long categoryId,
        String title,
        String description
) {
    public Post toEntity(User user, Games game, Categories categories) {
        return Post.builder()
                .user(user)
                .games(game)
                .categories(categories)
                .title(title)
                .description(description)
                .build();

    }
}

