package com.example.codePicasso.domain.gameProposal.service;

import com.example.codePicasso.domain.gameProposal.dto.request.CreateGameProposalRequest;
import com.example.codePicasso.domain.gameProposal.dto.request.ReviewGameProposalRequest;
import com.example.codePicasso.domain.gameProposal.dto.response.GameProposalResponse;
import com.example.codePicasso.domain.gameProposal.entity.GameProposal;
import com.example.codePicasso.domain.gameProposal.enums.ProposalStatus;
import com.example.codePicasso.domain.games.dto.request.GameRequest;
import com.example.codePicasso.domain.games.service.GameService;
import com.example.codePicasso.domain.users.entity.Admin;
import com.example.codePicasso.domain.users.entity.User;
import com.example.codePicasso.domain.users.service.AdminConnector;
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
    private final AdminConnector adminConnector;
    private final GameService gameService;

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

        return proposal.toDtoWithoutAdmin();
    }


    public GameProposalResponse reviewProposal(Long proposalId, ReviewGameProposalRequest request, Long adminId) {
        GameProposal proposal = gameProposalConnector.findById(proposalId);
        Admin foundAdmin = adminConnector.findById(adminId);

        proposal.update(foundAdmin, request.status());

        gameProposalConnector.save(proposal);

        if (proposal.getStatus() == ProposalStatus.APPROVED) {
            gameService.createGame(new GameRequest(proposal));
        }

        return proposal.toDtoWithAdmin(foundAdmin);
    }
}
