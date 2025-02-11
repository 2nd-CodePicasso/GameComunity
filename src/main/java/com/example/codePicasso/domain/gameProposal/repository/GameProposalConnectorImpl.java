package com.example.codePicasso.domain.gameProposal.repository;

import com.example.codePicasso.domain.gameProposal.entity.GameProposal;
import com.example.codePicasso.domain.gameProposal.service.GameProposalConnector;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
}
