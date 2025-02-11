package com.example.codePicasso.domain.gameProposal.controller;

import com.example.codePicasso.domain.gameProposal.dto.request.CreateGameProposalRequest;
import com.example.codePicasso.domain.gameProposal.dto.response.GameProposalResponse;
import com.example.codePicasso.domain.gameProposal.service.GameProposalService;
import com.example.codePicasso.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/proposals")
public class GameProposalController {

    private final GameProposalService gameProposalService;

    @PostMapping("/users")
    public ResponseEntity<ApiResponse<GameProposalResponse>> createGameProposal(
            @RequestBody CreateGameProposalRequest request
            , @RequestAttribute Long userId
    ) {
        GameProposalResponse proposal = gameProposalService.createProposal(request, userId);

        return ApiResponse.created(proposal);
    }

}
