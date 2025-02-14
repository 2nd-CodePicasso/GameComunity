package com.example.codePicasso.domain.posts.dto.request;

import com.example.codePicasso.domain.posts.entity.Post;

public record UpdateRequest(
        Long categoryId,
        String title,
        String description
) {
    public Post toEntity() {
        return Post.builder()
                .title(title)
                .description(description)
                .build();
    }
}
