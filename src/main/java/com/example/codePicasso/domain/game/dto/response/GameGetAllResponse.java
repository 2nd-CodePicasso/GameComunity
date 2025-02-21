package com.example.codePicasso.domain.game.dto.response;

import lombok.Builder;
import java.util.List;

@Builder
public record GameGetAllResponse(
        List<GameResponse> responses
) {
}
