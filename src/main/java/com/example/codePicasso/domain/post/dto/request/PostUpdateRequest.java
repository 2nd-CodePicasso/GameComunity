package com.example.codePicasso.domain.post.dto.request;

import com.example.codePicasso.domain.post.entity.Post;

public record PostUpdateRequest(
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
