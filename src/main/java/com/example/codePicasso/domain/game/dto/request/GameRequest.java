package com.example.codePicasso.domain.game.dto.request;

import com.example.codePicasso.domain.game.entity.Game;
import com.example.codePicasso.domain.gameProposal.entity.GameProposal;

public record GameRequest(
        GameProposal gameProposal
) {
    public Game toEntity() {
        return Game.builder()
                .admin(gameProposal.getAdmin())
                .gameTitle(gameProposal.getGameTitle())
                .gameDescription(gameProposal.getDescription())
                .build();
    }
}
