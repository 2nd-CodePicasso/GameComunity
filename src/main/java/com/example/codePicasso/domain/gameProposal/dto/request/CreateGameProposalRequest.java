package com.example.codePicasso.domain.gameProposal.dto.request;

import org.hibernate.validator.constraints.Length;

public record CreateGameProposalRequest(
        @Length(max = 20)
        String gameTitle,

        @Length(max = 255)
        String description
) {
}
