package com.example.codePicasso.domain.exchanges.controller;

import com.example.codePicasso.domain.exchanges.dto.request.ExchangeRequest;
import com.example.codePicasso.domain.exchanges.dto.response.ExchangeResponse;
import com.example.codePicasso.domain.exchanges.service.ExchangeService;
import com.example.codePicasso.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exchanges")
public class ExchangeController {

    private final ExchangeService exchangeService;

    //거래소 게시글 생성 (201 Created)
    @PostMapping
    public ResponseEntity<ApiResponse<ExchangeResponse>> addExchange(@RequestBody ExchangeRequest exchangeRequest) {
        return ApiResponse.created(exchangeService.createExchange(exchangeRequest));
    }

    // 전체 게임의 거래소 게시글 목록 조회 (200 OK)
    @GetMapping
    public ResponseEntity<ApiResponse<List<ExchangeResponse>>> getExchanges() {
        List<ExchangeResponse> responses = exchangeService.getExchanges();
        return ApiResponse.created(responses);
    }

    // 특정 게임의 거래소 게시글 목록 조회 (200 OK)
    @GetMapping("/{gameId}")
    public ResponseEntity<ApiResponse<List<ExchangeResponse>>> getExchange(@PathVariable Long gameId) {
        List<ExchangeResponse> responses = exchangeService.getExchangesByGameId(gameId);
        return ApiResponse.created(responses);
    }

    //거래소 게시글 수정 (200 OK)
    @PatchMapping("/{exchangeId}")
    public ResponseEntity<ApiResponse<ExchangeResponse>> updateExchange(
            @PathVariable Long exchangeId,
            @RequestBody ExchangeRequest request) {
        ExchangeResponse response = exchangeService.updateExchange(exchangeId, request);
        return ApiResponse.created(response);
    }

    //거래소 게시글 삭제 (204 No Content)
    @DeleteMapping("/{exchangeId}")
    public ResponseEntity<ApiResponse<Void>> deleteExchange(@PathVariable Long exchangeId) {
        exchangeService.deleteExchange(exchangeId);
        return ApiResponse.noContent();
    }
}
