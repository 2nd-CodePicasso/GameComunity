package com.example.codePicasso.domain.posts.dto.request;


import com.example.codePicasso.domain.games.entity.Games;
import com.example.codePicasso.domain.posts.entity.Categories;
import com.example.codePicasso.domain.posts.entity.Post;
import com.example.codePicasso.domain.users.entity.User;

public record PostCreateRequest(
        User user,
        Long gameId,
        Long categoryId,
        String title,
        String description
) {
    public Post toEntity(Games games, Categories categories) {
        return Post.builder()
                .user(user)
                .games(games)
                .categories(categories)
                .title(title)
                .description(description)
                .build();

    }
}

