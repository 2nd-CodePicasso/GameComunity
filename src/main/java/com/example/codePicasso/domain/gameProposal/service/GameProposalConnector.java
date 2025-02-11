package com.example.codePicasso.domain.gameProposal.service;

import com.example.codePicasso.domain.gameProposal.entity.GameProposal;
import org.springframework.stereotype.Component;

@Component
public interface GameProposalConnector {
    void save(GameProposal proposal);
    boolean existsByGameTitle(String gameTitle);

    GameProposal findById(Long proposalId);

}
