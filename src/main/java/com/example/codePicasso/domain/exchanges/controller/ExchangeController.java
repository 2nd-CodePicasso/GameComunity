package com.example.codePicasso.domain.exchanges.controller;

import com.example.codePicasso.domain.exchanges.dto.request.ExchangeRequest;
import com.example.codePicasso.domain.exchanges.dto.response.ExchangeResponse;
import com.example.codePicasso.domain.exchanges.entity.TradeType;
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

    // 구매 거래소 게시글 생성 (201 Created)
    @PostMapping("/buy")
    public ResponseEntity<ApiResponse<ExchangeResponse>> addBuyExchange(
            @RequestBody ExchangeRequest exchangeRequest,
            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return ApiResponse.created(exchangeService.createExchange(exchangeRequest, TradeType.BUY, userId));
    }

    // 판매 거래소 게시글 생성 (201 Created)
    @PostMapping("/sell")
    public ResponseEntity<ApiResponse<ExchangeResponse>> addSellExchange(
            @RequestBody ExchangeRequest exchangeRequest,
            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return ApiResponse.created(exchangeService.createExchange(exchangeRequest, TradeType.SELL, userId));
    }

//    // 전체 게임의 거래소 게시글 목록 조회 (200 OK)
//    @GetMapping
//    public ResponseEntity<ApiResponse<List<ExchangeResponse>>> getExchanges() {
//        List<ExchangeResponse> responses = exchangeService.getExchanges();
//        return ApiResponse.created(responses);
//    }
//
    // 구매 거래소 게시글 목록 조회 (200 OK)
    @GetMapping( "/buy")
    public ResponseEntity<ApiResponse<List<ExchangeResponse>>> getBuyExchange(
            @RequestParam(required = false) Long gameId
            ) {
        List<ExchangeResponse> responses = exchangeService.getBuyExchangesByGameId(gameId);
        return ApiResponse.success(responses);
    }

    // 판매 거래소 게시글 목록 조회 (200 OK)
    @GetMapping( "/sell")
    public ResponseEntity<ApiResponse<List<ExchangeResponse>>> getSellExchange(
            @RequestParam(required = false) Long gameId
            ) {
        List<ExchangeResponse> responses = exchangeService.getSellExchangesByGameId(gameId);
        return ApiResponse.success(responses);
    }

    // 거래소의 특정 게시글 조회 (200 OK)
    @GetMapping("/{exchangesId}")
    public ResponseEntity<ApiResponse<ExchangeResponse>> getExchange(@PathVariable Long exchangesId) {
        return ApiResponse.success(exchangeService.getExchangeById(exchangesId));
    }

    //거래소 게시글 수정 (200 OK)
    @PatchMapping("/{exchangeId}")
    public ResponseEntity<ApiResponse<ExchangeResponse>> updateExchange(
            @PathVariable Long exchangeId,
            @RequestBody ExchangeRequest request,
            HttpServletRequest httprequest
    ) {
        Long userId = (Long) httprequest.getAttribute("userId");
        ExchangeResponse response = exchangeService.updateExchange(exchangeId, request, userId);
        return ApiResponse.success(response);
    }

    //거래소 게시글 삭제 (204 No Content)
    @DeleteMapping("/{exchangeId}")
    public ResponseEntity<ApiResponse<Void>> deleteExchange(
            @PathVariable Long exchangeId,
            HttpServletRequest httprequest
    ) {
        Long userId = (Long) httprequest.getAttribute("userId");
        exchangeService.deleteExchange(exchangeId, userId);
        return ApiResponse.noContent();
    }
}
