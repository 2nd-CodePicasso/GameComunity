package com.example.codePicasso.domain.gameProposal.dto.request;

import com.example.codePicasso.domain.gameProposal.enums.ProposalStatus;
import jakarta.validation.constraints.NotNull;

public record ReviewGameProposalRequest(
        @NotNull
        ProposalStatus status
) {
}
