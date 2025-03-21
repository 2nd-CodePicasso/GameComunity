package com.example.codePicasso.domain.gameProposal.controller;

import com.example.codePicasso.domain.gameProposal.dto.request.CreateGameProposalRequest;
import com.example.codePicasso.domain.gameProposal.dto.request.ReviewGameProposalRequest;
import com.example.codePicasso.domain.gameProposal.dto.response.GameProposalGetManyResponse;
import com.example.codePicasso.domain.gameProposal.dto.response.GameProposalResponse;
import com.example.codePicasso.domain.gameProposal.enums.ProposalStatus;
import com.example.codePicasso.domain.gameProposal.service.GameProposalService;
import com.example.codePicasso.global.common.ApiResponse;
import com.example.codePicasso.global.common.CustomUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/proposals")
public class GameProposalController {
    private final GameProposalService gameProposalService;

    @PostMapping
    public ResponseEntity<ApiResponse<GameProposalResponse>> createGameProposal(
            @Valid @RequestBody CreateGameProposalRequest request,
            @AuthenticationPrincipal CustomUser user
    ) {
        GameProposalResponse response = gameProposalService.createProposal(request, user.getUserId());

        return ApiResponse.created(response);
    }

    @GetMapping("/admin/all")
    public ResponseEntity<ApiResponse<GameProposalGetManyResponse>> getAllProposalsApi() {
        GameProposalGetManyResponse response = gameProposalService.getAllProposals();

        return ApiResponse.success(response);
    }

    @GetMapping("/admin/status")
    public ResponseEntity<ApiResponse<GameProposalGetManyResponse>> getProposalsByStatusApi(
            @RequestParam ProposalStatus status
    ) {
        GameProposalGetManyResponse response = gameProposalService.getProposalsByStatus(status);

        return ApiResponse.success(response);
    }

    @GetMapping("/my-proposals")
    public ResponseEntity<ApiResponse<GameProposalGetManyResponse>> getMyProposalsApi(
            @AuthenticationPrincipal CustomUser user
    ) {
        GameProposalGetManyResponse response = gameProposalService.getProposalsByUserId(user.getUserId());

        return ApiResponse.success(response);
    }

    @PatchMapping("/{proposalId}")
    public ResponseEntity<ApiResponse<GameProposalResponse>> cancelMyProposal(
            @PathVariable Long proposalId,
            @AuthenticationPrincipal CustomUser user
    ) {
        GameProposalResponse response = gameProposalService.cancelProposal(proposalId, user.getUserId());

        return ApiResponse.success(response);
    }

    @PatchMapping("/admin/{proposalId}")
    public ResponseEntity<ApiResponse<GameProposalResponse>> updateGameProposal(
            @PathVariable Long proposalId,
            @Valid @RequestBody ReviewGameProposalRequest request,
            @AuthenticationPrincipal CustomUser user
    ) {
        GameProposalResponse response = gameProposalService.reviewProposal(proposalId, request, user.getUserId());

        return ApiResponse.success(response);
    }

}
