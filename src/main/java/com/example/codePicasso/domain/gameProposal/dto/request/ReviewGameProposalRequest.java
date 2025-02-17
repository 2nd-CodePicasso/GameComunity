package com.example.codePicasso.domain.gameProposal.dto.request;

import com.example.codePicasso.domain.gameProposal.enums.ProposalStatus;
import jakarta.validation.constraints.NotBlank;

public record ReviewGameProposalRequest(
        @NotBlank
        ProposalStatus status
) {
}
