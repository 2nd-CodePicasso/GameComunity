package com.example.codePicasso.domain.games.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateGameRequest(
        @NotBlank
        String gameDescription
) {
}
