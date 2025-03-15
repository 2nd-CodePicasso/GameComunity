package com.example.codePicasso.domain.gameProposal.repository;

import com.example.codePicasso.domain.gameProposal.entity.GameProposal;
import com.example.codePicasso.domain.gameProposal.enums.ProposalStatus;
import com.example.codePicasso.domain.gameProposal.service.GameProposalConnector;
import com.example.codePicasso.global.exception.base.NotFoundException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GameProposalConnectorImpl implements GameProposalConnector {
    private final GameProposalRepository repository;

    public GameProposal save(GameProposal proposal) {
        return repository.save(proposal);
    }

    @Override
    public boolean existsByGameTitle(String gameTitle) {
        return repository.existsByGameTitle(gameTitle);
    }

    @Override
    public GameProposal findById(Long proposalId) {
        return repository.findById(proposalId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.PROPOSAL_NOT_FOUND));
    }

    @Override
    public List<GameProposal> findAll() {
        return repository.findAll();
    }

    @Override
    public List<GameProposal> findAllByStatus(ProposalStatus status) {
        return repository.findAllByStatus(status);
    }

    @Override
    public List<GameProposal> findAllByUserId(Long userId) {
        return repository.findAllByUserId(userId);
    }
}
