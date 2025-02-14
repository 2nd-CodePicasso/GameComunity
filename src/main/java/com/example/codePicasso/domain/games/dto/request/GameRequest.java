package com.example.codePicasso.domain.games.dto.request;

import com.example.codePicasso.domain.gameProposal.entity.GameProposal;
import com.example.codePicasso.domain.games.entity.Games;

public record GameRequest(
        GameProposal gameProposal
) {
    public Games toEntity() {
        return Games.builder()
                .admin(gameProposal.getAdmin())
                .gameTitle(gameProposal.getGameTitle())
                .gameDescription(gameProposal.getDescription())
                .build();
    }
}
