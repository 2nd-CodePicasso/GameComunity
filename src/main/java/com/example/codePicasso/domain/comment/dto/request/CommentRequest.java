package com.example.codePicasso.domain.comment.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CommentRequest(
        @NotBlank
        String text
) {
}
