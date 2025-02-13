package com.example.codePicasso.domain.gameProposal.repository;

import com.example.codePicasso.domain.gameProposal.dto.response.GameProposalResponse;
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

    public void save(GameProposal proposal){
        repository.save(proposal);
    }

    @Override
    public boolean existsByGameTitle(String gameTitle) {
        return repository.existsByGameTitle(gameTitle);
    }

    @Override
    public GameProposal findById(Long proposalId) {
        return repository.findById(proposalId).orElseThrow(
                () -> new NotFoundException(ErrorCode.PROPOSAL_NOT_FOUND)
        );
    }

    @Override
    public List<GameProposalResponse> findAll() {
        return repository.findAllProposals();
    }

    @Override
    public List<GameProposalResponse> findByStatus(ProposalStatus status) {
        return repository.findByStatus(status);
    }

    @Override
    public List<GameProposalResponse> findByUserId(Long userId) {
        return repository.findByUserId(userId);
    }
}
