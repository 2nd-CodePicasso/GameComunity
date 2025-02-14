package com.example.codePicasso.domain.gameProposal.repository;

import com.example.codePicasso.domain.gameProposal.dto.response.GameProposalResponse;
import com.example.codePicasso.domain.gameProposal.entity.GameProposal;
import com.example.codePicasso.domain.gameProposal.enums.ProposalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GameProposalRepository extends JpaRepository<GameProposal, Long> {

    @Query("""
            SELECT new com.example.codePicasso.domain.gameProposal.dto.response.GameProposalResponse
            (gp.id, u.id, a.id, gp.gameTitle, gp.status)
            FROM GameProposal gp
            LEFT JOIN gp.user u
            LEFT JOIN gp.admin a
            """)
    List<GameProposalResponse> findAllProposals();

    boolean existsByGameTitle (String gameTitle);

    @Query("""
            SELECT new com.example.codePicasso.domain.gameProposal.dto.response.GameProposalResponse
            (gp.id, u.id, a.id, gp.gameTitle, gp.status)
            FROM GameProposal gp
            LEFT JOIN gp.user u
            LEFT JOIN gp.admin a
            WHERE gp.status = :status
            """)
    List<GameProposalResponse> findByStatus (ProposalStatus status);

    @Query("""
            SELECT new com.example.codePicasso.domain.gameProposal.dto.response.GameProposalResponse
            (gp.id, u.id, a.id, gp.gameTitle, gp.status)
            FROM GameProposal gp
            LEFT JOIN gp.user u
            LEFT JOIN gp.admin a
            WHERE u.id = :userId
            """)
    List<GameProposalResponse> findByUserId (Long userId);
}
