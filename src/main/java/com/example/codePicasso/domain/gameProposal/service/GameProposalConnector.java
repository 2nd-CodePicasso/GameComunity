package com.example.codePicasso.domain.gameProposal.service;

import com.example.codePicasso.domain.gameProposal.entity.GameProposal;
import com.example.codePicasso.domain.gameProposal.enums.ProposalStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface GameProposalConnector {
    void save(GameProposal proposal);
    boolean existsByGameTitle(String gameTitle);

    GameProposal findById(Long proposalId);

    List<GameProposal> findAll();

    List<GameProposal> findByStatus(ProposalStatus status);
}
