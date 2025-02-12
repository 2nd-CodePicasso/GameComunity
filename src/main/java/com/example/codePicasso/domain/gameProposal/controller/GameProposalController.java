package com.example.codePicasso.domain.gameProposal.controller;

import com.example.codePicasso.domain.gameProposal.dto.request.CreateGameProposalRequest;
import com.example.codePicasso.domain.gameProposal.dto.request.ReviewGameProposalRequest;
import com.example.codePicasso.domain.gameProposal.dto.response.GameProposalResponse;
import com.example.codePicasso.domain.gameProposal.service.GameProposalService;
import com.example.codePicasso.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/proposals")
public class GameProposalController {

    private final GameProposalService gameProposalService;

    @PostMapping
    public ResponseEntity<ApiResponse<GameProposalResponse>> createGameProposal(
            @RequestBody CreateGameProposalRequest request
            , @RequestAttribute Long userId
    ) {
        GameProposalResponse response = gameProposalService.createProposal(request, userId);

        return ApiResponse.created(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<GameProposalResponse>>> getAllProposalsApi() {
        List<GameProposalResponse> response = gameProposalService.getAllProposals();
        return ApiResponse.success(response);
    }


    @PatchMapping("/{proposalId}/admin")
    public ResponseEntity<ApiResponse<GameProposalResponse>> updateGameProposal(
            @PathVariable Long proposalId, @RequestBody ReviewGameProposalRequest request
            , @RequestAttribute Long adminId
    ) {
        GameProposalResponse response = gameProposalService.reviewProposal(proposalId, request, adminId);

        return ApiResponse.success(response);
    }

}
