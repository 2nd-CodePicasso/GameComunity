package com.example.codePicasso.domain.gameProposal.service;

import com.example.codePicasso.domain.gameProposal.dto.request.CreateGameProposalRequest;
import com.example.codePicasso.domain.gameProposal.entity.GameProposal;
import com.example.codePicasso.domain.users.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameProposalService {

    private final GameProposalConnector gameProposalConnector;

    public void createProposal(CreateGameProposalRequest request, Long userId) {
    }
}
