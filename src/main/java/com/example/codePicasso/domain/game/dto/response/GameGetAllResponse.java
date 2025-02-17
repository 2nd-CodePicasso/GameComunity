package com.example.codePicasso.domain.game.dto.response;

import java.util.List;

public record GameGetAllResponse(
        List<GameResponse> responses
) {
}
