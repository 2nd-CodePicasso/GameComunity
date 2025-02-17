package com.example.codePicasso.domain.gameProposal.controller;

import com.example.codePicasso.domain.gameProposal.dto.request.CreateGameProposalRequest;
import com.example.codePicasso.domain.gameProposal.dto.request.ReviewGameProposalRequest;
import com.example.codePicasso.domain.gameProposal.dto.response.GameProposalGetManyResponse;
import com.example.codePicasso.domain.gameProposal.dto.response.GameProposalResponse;
import com.example.codePicasso.domain.gameProposal.enums.ProposalStatus;
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

    @PostMapping
    public ResponseEntity<ApiResponse<GameProposalResponse>> createGameProposal(
            @RequestBody CreateGameProposalRequest request,
            @RequestAttribute Long userId
    ) {
        GameProposalResponse response = gameProposalService.createProposal(request, userId);

        return ApiResponse.created(response);
    }

    @GetMapping("/all/admin")
    public ResponseEntity<ApiResponse<GameProposalGetManyResponse>> getAllProposalsApi() {
        GameProposalGetManyResponse response = gameProposalService.getAllProposals();
        return ApiResponse.success(response);
    }

    @GetMapping("/status/admin")
    public ResponseEntity<ApiResponse<GameProposalGetManyResponse>> getProposalsByStatusApi(
            @RequestParam ProposalStatus status
    ) {
        GameProposalGetManyResponse response = gameProposalService.getProposalsByStatus(status);
        return ApiResponse.success(response);
    }

    @GetMapping("/my-proposals")
    public ResponseEntity<ApiResponse<GameProposalGetManyResponse>> getMyProposalsApi(
            @RequestAttribute Long userId
    ) {
        GameProposalGetManyResponse response = gameProposalService.getMyProposals(userId);
        return ApiResponse.success(response);
    }

    @PatchMapping("/{proposalId}")
    public ResponseEntity<ApiResponse<GameProposalResponse>> cancelMyProposal(
            @PathVariable Long proposalId,
            @RequestAttribute Long userId
    ) {
        GameProposalResponse response = gameProposalService.cancelProposal(proposalId, userId);

        return ApiResponse.success(response);
    }

    @PatchMapping("/{proposalId}/admin")
    public ResponseEntity<ApiResponse<GameProposalResponse>> updateGameProposal(
            @PathVariable Long proposalId,
            @RequestBody ReviewGameProposalRequest request,
            @RequestAttribute Long adminId
    ) {
        GameProposalResponse response = gameProposalService.reviewProposal(proposalId, request, adminId);

        return ApiResponse.success(response);
    }

}
