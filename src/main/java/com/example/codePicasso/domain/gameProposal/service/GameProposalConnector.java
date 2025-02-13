package com.example.codePicasso.domain.gameProposal.service;

import com.example.codePicasso.domain.gameProposal.dto.response.GameProposalResponse;
import com.example.codePicasso.domain.gameProposal.entity.GameProposal;
import com.example.codePicasso.domain.gameProposal.enums.ProposalStatus;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public interface GameProposalConnector {
    void save(GameProposal proposal);
    boolean existsByGameTitle(String gameTitle);

    GameProposal findById(Long proposalId);

    List<GameProposalResponse> findAll();

    List<GameProposalResponse> findByStatus(ProposalStatus status);

    List<GameProposalResponse> findByUserId(Long userId);
}
