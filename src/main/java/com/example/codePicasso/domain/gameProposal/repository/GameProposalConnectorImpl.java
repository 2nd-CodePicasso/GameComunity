package com.example.codePicasso.domain.gameProposal.repository;

import com.example.codePicasso.domain.gameProposal.entity.GameProposal;
import com.example.codePicasso.domain.gameProposal.enums.ProposalStatus;
import com.example.codePicasso.domain.gameProposal.service.GameProposalConnector;
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
        return repository.findById(proposalId).orElseThrow();
    }

    @Override
    public List<GameProposal> findAll() {
        return repository.findAll();
    }

    @Override
    public List<GameProposal> findByStatus(ProposalStatus status) {
        return repository.findByStatus(status);
    }

}
