package com.example.codePicasso.domain.gameProposal.dto.request;

import com.example.codePicasso.domain.gameProposal.enums.ProposalStatus;

public record ReviewGameProposalRequest(
        ProposalStatus status
) {
}
