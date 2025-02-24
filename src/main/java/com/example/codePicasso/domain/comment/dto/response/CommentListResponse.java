package com.example.codePicasso.domain.comment.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record CommentListResponse(
        List<CommentResponse> responses
) {
}
