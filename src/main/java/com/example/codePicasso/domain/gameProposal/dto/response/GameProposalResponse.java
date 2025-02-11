package com.example.codePicasso.domain.gameProposal.dto.response;

import com.example.codePicasso.domain.gameProposal.enums.ProposalStatus;
import lombok.Builder;

@Builder
public record GameProposalResponse(
        Long id,
        Long userId,
        String gameTitle,
        ProposalStatus status
) {
}
