package com.example.codePicasso.domain.gameProposal.service;

import com.example.codePicasso.domain.game.dto.request.GameRequest;
import com.example.codePicasso.domain.game.service.GameService;
import com.example.codePicasso.domain.gameProposal.dto.request.CreateGameProposalRequest;
import com.example.codePicasso.domain.gameProposal.dto.request.ReviewGameProposalRequest;
import com.example.codePicasso.domain.gameProposal.dto.response.GameProposalGetManyResponse;
import com.example.codePicasso.domain.gameProposal.dto.response.GameProposalResponse;
import com.example.codePicasso.domain.gameProposal.entity.GameProposal;
import com.example.codePicasso.domain.gameProposal.enums.ProposalStatus;
import com.example.codePicasso.domain.user.entity.Admin;
import com.example.codePicasso.domain.user.entity.User;
import com.example.codePicasso.domain.user.service.AdminConnector;
import com.example.codePicasso.domain.user.service.UserConnector;
import com.example.codePicasso.global.common.DtoFactory;
import com.example.codePicasso.global.exception.base.AccessDeniedException;
import com.example.codePicasso.global.exception.base.ConflictException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GameProposalService {

    private final GameProposalConnector gameProposalConnector;
    private final UserConnector userConnector;
    private final AdminConnector adminConnector;
    private final GameService gameService;

    @Transactional
    public GameProposalResponse createProposal(CreateGameProposalRequest request, Long userId) {
        User foundUser = userConnector.findById(userId);

        ProposalStatus status = gameProposalConnector.existsByGameTitle(request.gameTitle())
                ? ProposalStatus.REJECTED : ProposalStatus.WAITING;

        GameProposal proposal = request.toEntity(foundUser,status);

        GameProposal save = gameProposalConnector.save(proposal);

        return DtoFactory.toGameProposalDto(save);
    }

    public GameProposalGetManyResponse getAllProposals() {
        return new GameProposalGetManyResponse(gameProposalConnector.findAll().stream()
                .map(DtoFactory::toGameProposalDto).toList());
    }

    public GameProposalGetManyResponse getProposalsByStatus(ProposalStatus status) {
        return new GameProposalGetManyResponse(gameProposalConnector.findAllByStatus(status).stream()
                .map(DtoFactory::toGameProposalDto).toList());
    }

    public GameProposalGetManyResponse getMyProposals(Long userId) {
        return new GameProposalGetManyResponse(gameProposalConnector.findAllByUserId(userId).stream()
                .map(DtoFactory::toGameProposalDto).toList());
    }

    @Transactional
    public GameProposalResponse cancelProposal(Long proposalId, Long userId) {
        GameProposal foundProposal = gameProposalConnector.findById(proposalId);
        if (foundProposal.getStatus() != ProposalStatus.WAITING) {
            throw new ConflictException(ErrorCode.PROPOSAL_ALREADY_REVIEWED);
        }
        if (!foundProposal.getUser().getId().equals(userId)) {
            throw new AccessDeniedException(ErrorCode.NOT_YOUR_PROPOSAL);
        }
        foundProposal.updateStatus(ProposalStatus.CANCELED);
        return DtoFactory.toGameProposalDto(foundProposal);
    }

    @Transactional
    public GameProposalResponse reviewProposal(Long proposalId, ReviewGameProposalRequest request, Long adminId) {
        GameProposal proposal = gameProposalConnector.findById(proposalId);
        Admin foundAdmin = adminConnector.findById(adminId);

        proposal.update(foundAdmin, request.status());

        if (proposal.getStatus() == ProposalStatus.APPROVED) {
            gameService.createGame(new GameRequest(proposal));
        }

        return DtoFactory.toGameProposalDto(proposal);
    }
}
