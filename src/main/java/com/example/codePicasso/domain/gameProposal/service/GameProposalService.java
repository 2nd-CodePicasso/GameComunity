package com.example.codePicasso.domain.gameProposal.service;

import com.example.codePicasso.domain.gameProposal.dto.request.CreateGameProposalRequest;
import com.example.codePicasso.domain.gameProposal.dto.response.GameProposalResponse;
import com.example.codePicasso.domain.gameProposal.entity.GameProposal;
import com.example.codePicasso.domain.gameProposal.enums.ProposalStatus;
import com.example.codePicasso.domain.users.entity.User;
import com.example.codePicasso.domain.users.service.UserConnector;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameProposalService {

    private final GameProposalConnector gameProposalConnector;
    private final UserConnector userConnector;

    public GameProposalResponse createProposal(CreateGameProposalRequest request, Long userId) {
        User foundUser = userConnector.findById(userId);

        ProposalStatus status = gameProposalConnector.existsByGameTitle(request.gameTitle())
                ? ProposalStatus.REJECTED : ProposalStatus.WAITING;

        GameProposal proposal = GameProposal.builder()
                .user(foundUser)
                .gameTitle(request.gameTitle())
                .status(status)
                .build();

        gameProposalConnector.save(proposal);

        return proposal.toDto();
    }
}
