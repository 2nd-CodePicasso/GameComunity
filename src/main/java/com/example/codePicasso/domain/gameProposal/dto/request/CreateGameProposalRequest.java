package com.example.codePicasso.domain.gameProposal.dto.request;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CreateGameProposalRequest(
        @NotBlank
        @Length(max = 20)
        String gameTitle,

        @NotBlank
        @Length(max = 255)
        String description
) {
}
