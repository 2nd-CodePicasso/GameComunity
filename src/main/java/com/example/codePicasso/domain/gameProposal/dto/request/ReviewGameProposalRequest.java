package com.example.codePicasso.domain.gameProposal.dto.request;

import com.example.codePicasso.domain.gameProposal.enums.ProposalStatus;
import jakarta.validation.constraints.NotBlank;

public record ReviewGameProposalRequest(
        //Todo - @NotBlank는 문자열에 사용 Enum은 @NotNull
        @NotBlank
        ProposalStatus status
) {
}
