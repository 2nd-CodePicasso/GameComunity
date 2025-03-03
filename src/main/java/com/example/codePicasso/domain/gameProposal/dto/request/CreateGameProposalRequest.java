package com.example.codePicasso.domain.gameProposal.dto.request;

import com.example.codePicasso.domain.gameProposal.entity.GameProposal;
import com.example.codePicasso.domain.gameProposal.enums.ProposalStatus;
import com.example.codePicasso.domain.user.entity.User;
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
    public GameProposal toEntity(User user, ProposalStatus status) {
        return GameProposal.builder()
                .user(user)
                .gameTitle(gameTitle)
                .description(description)
                .status(status)
                .build();

    }
}
