package com.example.codePicasso.domain.exchange.controller;

import com.example.codePicasso.domain.exchange.dto.request.ExchangeRequest;
import com.example.codePicasso.domain.exchange.dto.response.ExchangeResponse;
import com.example.codePicasso.domain.exchange.entity.TradeType;
import com.example.codePicasso.domain.exchange.service.ExchangeService;
import com.example.codePicasso.global.common.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exchanges")
public class ExchangeController {

    private final ExchangeService exchangeService;

    // 구매 거래소 게시글 생성 (201 Created)
    @PostMapping("/buy")
    public ResponseEntity<ApiResponse<ExchangeResponse>> addBuyExchange(
            @Valid @RequestBody ExchangeRequest exchangeRequest,
            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return ApiResponse.created(exchangeService.createExchange(exchangeRequest, TradeType.BUY, userId));
    }

    // 판매 거래소 게시글 생성 (201 Created)
    @PostMapping("/sell")
    public ResponseEntity<ApiResponse<ExchangeResponse>> addSellExchange(
            @Valid @RequestBody ExchangeRequest exchangeRequest,
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
    @GetMapping( "/buy/list")
    public ResponseEntity<ApiResponse<Page<ExchangeResponse>>> getBuyExchange(
            @RequestParam(required = false) Long gameId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
            ) {
        Page<ExchangeResponse> responses = exchangeService.getBuyExchangesByGameId(gameId, page, size);
        return ApiResponse.success(responses);
    }

    // 판매 거래소 게시글 목록 조회 (200 OK)
    @GetMapping( "/sell/list")
    public ResponseEntity<ApiResponse<Page<ExchangeResponse>>> getSellExchange(
            @RequestParam(required = false) Long gameId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
            ) {
        Page<ExchangeResponse> responses = exchangeService.getSellExchangesByGameId(gameId, page, size);
        return ApiResponse.success(responses);
    }

    // 거래소의 구매 게시글 세부페이지 조회 (200 OK)
    @GetMapping("/buy/{exchangesId}")
    public ResponseEntity<ApiResponse<ExchangeResponse>> getDetailBuyExchange(@PathVariable Long exchangesId) {
        return ApiResponse.success(exchangeService.getExchangeById(exchangesId));
    }

    // 거래소의 구매 게시글 세부페이지 조회 (200 OK)
    @GetMapping("/sell/{exchangesId}")
    public ResponseEntity<ApiResponse<ExchangeResponse>> getDetailSellExchange(@PathVariable Long exchangesId) {
        return ApiResponse.success(exchangeService.getExchangeById(exchangesId));
    }

    //거래소 게시글 수정 (200 OK)
    @PatchMapping("/{exchangeId}")
    public ResponseEntity<ApiResponse<ExchangeResponse>> updateExchange(
            @PathVariable Long exchangeId,
            @Valid @RequestBody ExchangeRequest request,
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
