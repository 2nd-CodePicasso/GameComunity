package com.example.codePicasso.domain.post.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record PostListResponse(
        List<PostResponse> postResponses
) {
}
