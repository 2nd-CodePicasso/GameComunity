package com.example.codePicasso.domain.post.dto.request;

public record PostRequest(
        Long categoryId,
        String title,
        String description
) {
}