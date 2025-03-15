package com.example.codePicasso.domain.auth.dto.response;

import lombok.Builder;

@Builder
public record JwtTokenResponse(
        String token
) {
}
