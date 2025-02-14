package com.example.codePicasso.domain.game.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateGameRequest(
        @NotBlank
        String gameDescription
) {
}
