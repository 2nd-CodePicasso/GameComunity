package com.example.codePicasso.domain.gameProposal.dto.response;

import java.util.List;

public record GameProposalGetManyResponse(
        List<GameProposalResponse> proposals
) {
}
