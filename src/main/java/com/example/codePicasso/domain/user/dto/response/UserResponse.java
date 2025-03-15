package com.example.codePicasso.domain.user.dto.response;

import lombok.Builder;

@Builder
public record UserResponse(
        String loginId,
        String nickname
) {
}
