package com.example.codePicasso.domain.exchange.controller;

import com.example.codePicasso.domain.exchange.dto.request.ExchangeRequest;
import com.example.codePicasso.domain.exchange.dto.request.MyExchangeRequest;
import com.example.codePicasso.domain.exchange.dto.request.PutMyExchangeRequest;
import com.example.codePicasso.domain.exchange.dto.response.ExchangeListResponse;
import com.example.codePicasso.domain.exchange.dto.response.ExchangeResponse;
import com.example.codePicasso.domain.exchange.dto.response.MyExchangeListResponse;
import com.example.codePicasso.domain.exchange.dto.response.MyExchangeResponse;
import com.example.codePicasso.domain.exchange.entity.TradeType;
import com.example.codePicasso.domain.exchange.service.ExchangeService;
import com.example.codePicasso.global.common.ApiResponse;
import com.example.codePicasso.global.common.CustomUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exchanges")
public class ExchangeController {

    private final ExchangeService exchangeService;

    /// --- ↓ Exchange ---

    // 거래소 게시글 생성 (201 Created)
    @PostMapping("/{tradeType}")
    public ResponseEntity<ApiResponse<ExchangeResponse>> createExchange(
            @Valid @RequestBody ExchangeRequest exchangeRequest,
            @PathVariable TradeType tradeType,
            @AuthenticationPrincipal CustomUser user
    ) {
        return ApiResponse.created(exchangeService.createExchange(exchangeRequest, tradeType, user.getUserId()));
    }

    // 거래소 게시글 목록 조회 (200 OK)
    @GetMapping( "/{tradeType}/list")
    public ResponseEntity<ApiResponse<ExchangeListResponse>> getAllExchange(
            @PathVariable TradeType tradeType,
            @RequestParam(required = false) Long gameId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<ExchangeResponse> responses = exchangeService.getExchanges(tradeType, gameId, page, size);
        return ApiResponse.success(ExchangeListResponse.builder().exchangePageResponse(responses).build());
    }

    // 거래소의 판매 게시글 세부페이지 조회 (200 OK)
    @GetMapping("/{tradeType}/{exchangeId}")
    public ResponseEntity<ApiResponse<ExchangeResponse>> getExchangeByExchangeId(
            @PathVariable TradeType tradeType,
            @PathVariable Long exchangeId
    ) {
        return ApiResponse.success(exchangeService.getExchangeById(exchangeId));
    }

    // 거래소 게시글 수정 (200 OK)
    @PatchMapping("/{exchangeId}")
    public ResponseEntity<ApiResponse<ExchangeResponse>> updateExchange(
            @PathVariable Long exchangeId,
            @Valid @RequestBody ExchangeRequest request,
            @AuthenticationPrincipal CustomUser user
    ) {
        return ApiResponse.success(exchangeService.updateExchange(exchangeId, request, user.getUserId()));
    }

    //거래소 게시글 삭제 (204 No Content)
    @DeleteMapping("/{exchangeId}")
    public ResponseEntity<ApiResponse<Void>> deleteExchange(
            @PathVariable Long exchangeId,
            @AuthenticationPrincipal CustomUser user
    ) {
        exchangeService.deleteExchange(exchangeId, user.getUserId());
        return ApiResponse.noContent();
    }

    /// --- ↑ Exchange ---

    ///  --- ↓ MyExchange ---

    // 판매하기 & 구매하기 (200 OK)
    @PostMapping("/{tradeType}/{exchangeId}")
    public ResponseEntity<ApiResponse<MyExchangeResponse>> doExchange(
            @PathVariable TradeType tradeType,
            @PathVariable Long exchangeId,
            @AuthenticationPrincipal CustomUser user,
            @RequestBody MyExchangeRequest request
    ) {
        MyExchangeResponse response =  exchangeService.doExchange(exchangeId, user.getUserId(), request);
        return ApiResponse.success(response);
    }

    // 내 거래 목록 조회 (200 OK)
    @GetMapping("/myList/{tradeType}")
    public ResponseEntity<ApiResponse<MyExchangeListResponse>> getAllMyExchange(
            @PathVariable TradeType tradeType,
            @AuthenticationPrincipal CustomUser user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<MyExchangeResponse> responses =  exchangeService.getAllMyExchange(tradeType, user.getUserId(), page, size);
        return ApiResponse.success(MyExchangeListResponse.builder().myExchangePageResponse(responses).build());
    }

    // 내 거래 목록 단일 조회 (200 OK)
    @GetMapping("/myList/{tradeType}/{myExchangeId}")
    public ResponseEntity<ApiResponse<MyExchangeResponse>> getMyExchangeById(
            @PathVariable TradeType tradeType,
            @PathVariable Long myExchangeId,
            @AuthenticationPrincipal CustomUser user
    ) {
        return ApiResponse.success(exchangeService.getMyExchangeById(myExchangeId, user));
    }

    // 거래 승인/거절/취소하기 (200 OK)
    @PutMapping("/myList/{tradeType}/{myExchangeId}")
    public ResponseEntity<ApiResponse<Void>> decisionMyExchange(
            @PathVariable TradeType tradeType,
            @PathVariable Long myExchangeId,
            @AuthenticationPrincipal CustomUser user,
            @RequestBody PutMyExchangeRequest request
    ) {
        exchangeService.decisionMyExchange(myExchangeId, user.getUserId(), request);
        return ApiResponse.noContent();
    }

    /// --- ↑ MyExchange ---
}