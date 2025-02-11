package com.example.codePicasso.domain.gameProposal.repository;

import com.example.codePicasso.domain.gameProposal.entity.GameProposal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameProposalRepository extends JpaRepository<GameProposal, Long> {

    public boolean existsByGameTitle (String gameTitle);
}
