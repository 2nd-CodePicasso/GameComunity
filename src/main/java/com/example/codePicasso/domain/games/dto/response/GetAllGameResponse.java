package com.example.codePicasso.domain.games.dto.response;

import java.util.List;

public record GetAllGameResponse(
        List<String> gameTitles
) {
}
