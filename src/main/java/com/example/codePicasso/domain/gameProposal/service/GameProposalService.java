package com.example.codePicasso.domain.gameProposal.service;

import com.example.codePicasso.domain.gameProposal.dto.request.CreateGameProposalRequest;
import com.example.codePicasso.domain.gameProposal.dto.request.ReviewGameProposalRequest;
import com.example.codePicasso.domain.gameProposal.dto.response.GameProposalResponse;
import com.example.codePicasso.domain.gameProposal.entity.GameProposal;
import com.example.codePicasso.domain.gameProposal.enums.ProposalStatus;
import com.example.codePicasso.domain.game.dto.request.GameRequest;
import com.example.codePicasso.domain.game.service.GameService;
import com.example.codePicasso.domain.users.entity.Admin;
import com.example.codePicasso.domain.users.entity.User;
import com.example.codePicasso.domain.users.service.AdminConnector;
import com.example.codePicasso.domain.users.service.UserConnector;
import com.example.codePicasso.global.exception.base.AccessDeniedException;
import com.example.codePicasso.global.exception.base.ConflictException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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
                .description(request.description())
                .status(status)
                .build();

        gameProposalConnector.save(proposal);

        return proposal.toDto();
    }

    public List<GameProposalResponse> getAllProposals() {
        return gameProposalConnector.findAll();
    }

    public List<GameProposalResponse> getProposalsByStatus(ProposalStatus status) {
        return gameProposalConnector.findByStatus(status);
    }

    public List<GameProposalResponse> getMyProposals(Long userId) {
        return gameProposalConnector.findByUserId(userId);
    }

    public GameProposalResponse cancelProposal(Long proposalId, Long userId) {
        GameProposal foundProposal = gameProposalConnector.findById(proposalId);
        if (foundProposal.getStatus() != ProposalStatus.WAITING) {
            throw new ConflictException(ErrorCode.PROPOSAL_ALREADY_REVIEWED);
        }
        if (!foundProposal.getUser().getId().equals(userId)) {
            throw new AccessDeniedException(ErrorCode.NOT_YOUR_PROPOSAL);
        }
        foundProposal.update(null, ProposalStatus.CANCELED);
        gameProposalConnector.save(foundProposal);
        return foundProposal.toDto();
    }

    public GameProposalResponse reviewProposal(Long proposalId, ReviewGameProposalRequest request, Long adminId) {
        GameProposal proposal = gameProposalConnector.findById(proposalId);
        Admin foundAdmin = adminConnector.findById(adminId);

        proposal.update(foundAdmin, request.status());

        gameProposalConnector.save(proposal);

        if (proposal.getStatus() == ProposalStatus.APPROVED) {
            gameService.createGame(new GameRequest(proposal));
        }

        return proposal.toDto();
    }
}
