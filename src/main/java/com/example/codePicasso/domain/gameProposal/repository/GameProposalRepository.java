package com.example.codePicasso.domain.gameProposal.repository;

import com.example.codePicasso.domain.gameProposal.entity.GameProposal;
import com.example.codePicasso.domain.gameProposal.enums.ProposalStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameProposalRepository extends JpaRepository<GameProposal, Long> {

    boolean existsByGameTitle(String gameTitle);

    List<GameProposal> findByStatus(ProposalStatus status);

    List<GameProposal> findByUserId(Long userId);
}
