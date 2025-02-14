package com.example.codePicasso.domain.game.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record GameResponse(

        Long id,
        String gameTitle,
        String gameDescription,
        LocalDateTime created_at,
        LocalDateTime updated_at

) {

}
