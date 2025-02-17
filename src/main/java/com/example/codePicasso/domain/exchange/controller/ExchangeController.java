package com.example.codePicasso.domain.exchange.controller;

import com.example.codePicasso.domain.exchange.dto.request.ExchangeRequest;
import com.example.codePicasso.domain.exchange.dto.request.MyExchangeRequest;
import com.example.codePicasso.domain.exchange.dto.request.PutExchangeRequest;
import com.example.codePicasso.domain.exchange.dto.response.ExchangeListResponse;
import com.example.codePicasso.domain.exchange.dto.response.ExchangeResponse;
import com.example.codePicasso.domain.exchange.dto.response.MyExchangeListResponse;
import com.example.codePicasso.domain.exchange.dto.response.MyExchangeResponse;
import com.example.codePicasso.domain.exchange.entity.StatusType;
import com.example.codePicasso.domain.exchange.entity.TradeType;
import com.example.codePicasso.domain.exchange.service.ExchangeService;
import com.example.codePicasso.global.common.ApiResponse;
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
    public ResponseEntity<ApiResponse<ExchangeResponse>> createBuyExchange(
            @Valid @RequestBody ExchangeRequest exchangeRequest,
            //authenti ~~~ 성우님 코드를 받은 이후에 진행.
            @RequestAttribute Long userId
    ) {
        return ApiResponse.created(exchangeService.createExchange(exchangeRequest, TradeType.BUY, userId));
    }

    // 판매 거래소 게시글 생성 (201 Created)
    @PostMapping("/sell")
    public ResponseEntity<ApiResponse<ExchangeResponse>> createSellExchange(
            @Valid @RequestBody ExchangeRequest exchangeRequest,
            @RequestAttribute Long userId
    ) {
        return ApiResponse.created(exchangeService.createExchange(exchangeRequest, TradeType.SELL, userId));
    }

    // 구매 거래소 게시글 목록 조회 (200 OK)
    @GetMapping( "/buy/list")
    public ResponseEntity<ApiResponse<ExchangeListResponse>> getAllBuy(
            @RequestParam(required = false) Long gameId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<ExchangeResponse> responses = exchangeService.getBuyExchanges(gameId, page, size);
        return ApiResponse.success(ExchangeListResponse.builder().exchangePageResponse(responses).build());
    }

    // 판매 거래소 게시글 목록 조회 (200 OK)
    @GetMapping( "/sell/list")
    public ResponseEntity<ApiResponse<ExchangeListResponse>> getAllSell(
            @RequestParam(required = false) Long gameId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<ExchangeResponse> responses = exchangeService.getSellExchanges(gameId, page, size);
        return ApiResponse.success(ExchangeListResponse.builder().exchangePageResponse(responses).build());
    }

    // 거래소의 구매 게시글 세부페이지 조회 (200 OK)
    @GetMapping("/buy/{exchangeId}")
    public ResponseEntity<ApiResponse<ExchangeResponse>> getBuyByExchangeId(
            @PathVariable Long exchangeId
    ) {
        return ApiResponse.success(exchangeService.getExchangeById(exchangeId));
    }

    // 거래소의 판매 게시글 세부페이지 조회 (200 OK)
    @GetMapping("/sell/{exchangeId}")
    public ResponseEntity<ApiResponse<ExchangeResponse>> getSellByExchangeId(
            @PathVariable Long exchangeId
    ) {
        return ApiResponse.success(exchangeService.getExchangeById(exchangeId));
    }

    //거래소 게시글 수정 (200 OK)
    @PatchMapping("/{exchangeId}")
    public ResponseEntity<ApiResponse<ExchangeResponse>> updateExchange(
            @PathVariable Long exchangeId,
            @Valid @RequestBody ExchangeRequest request,
            @RequestAttribute Long userId
    ) {
        ExchangeResponse response = exchangeService.updateExchange(exchangeId, request, userId);
        return ApiResponse.success(response);
    }

    //거래소 게시글 삭제 (204 No Content)
    @DeleteMapping("/{exchangeId}")
    public ResponseEntity<ApiResponse<Void>> deleteExchange(
            @PathVariable Long exchangeId,
            @RequestAttribute Long userId
    ) {
        exchangeService.deleteExchange(exchangeId, userId);
        return ApiResponse.noContent();
    }

    //판매하기
    @PostMapping("/buy/{exchangeId}")
    public ResponseEntity<ApiResponse<MyExchangeResponse>> buyExchange(
            @PathVariable Long exchangeId,
            @RequestAttribute Long userId,
            @RequestBody MyExchangeRequest request
    ) {
        MyExchangeResponse response = exchangeService.doExchange(exchangeId, userId, request);
        return ApiResponse.success(response);
    }

    //구매하기
    @PostMapping("/sell/{exchangeId}")
    public ResponseEntity<ApiResponse<MyExchangeResponse>> sellExchange(
            @PathVariable Long exchangeId,
            @RequestAttribute Long userId,
            @RequestBody MyExchangeRequest request
    ) {
        MyExchangeResponse response =  exchangeService.doExchange(exchangeId, userId, request);
        return ApiResponse.success(response);
    }

    //내 구매 거래 목록 조회 (200 OK)
    @GetMapping("/myList/buy")
    public ResponseEntity<ApiResponse<MyExchangeListResponse>> getMyBuy(
            @RequestAttribute Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<MyExchangeResponse> responses =  exchangeService.getMyBuyExchanges(userId, page, size);
        return ApiResponse.success(MyExchangeListResponse.builder().myExchangePageResponse(responses).build());
    }

    //내 판매 거래 목록 조회 (200 OK)
    @GetMapping("/myList/sell")
    public ResponseEntity<ApiResponse<MyExchangeListResponse>> getMySell(
            @RequestAttribute Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<MyExchangeResponse> responses =  exchangeService.getMySellExchanges(userId, page, size);
        return ApiResponse.success(MyExchangeListResponse.builder().myExchangePageResponse(responses).build());
    }

    // buy_취소하기
    @PutMapping("/list/buy/{myExchangeId}")
    public ResponseEntity<ApiResponse<Void>> cancelBuyExchange(
            @PathVariable Long myExchangeId,
            @RequestAttribute Long userId,
            @RequestBody PutExchangeRequest putExchangeRequest
    ) {
        exchangeService.putExchange(myExchangeId, userId, putExchangeRequest);
        return ApiResponse.noContent();
    }

    // sell_거래 승인/거절하기
    @PutMapping("/list/sell/{myExchangeId}")
    public ResponseEntity<ApiResponse<Void>> approveSellExchange(
            @PathVariable Long myExchangeId,
            @RequestAttribute Long userId,
            @RequestBody PutExchangeRequest putExchangeRequest
    ) {
        exchangeService.putExchange(myExchangeId, userId, putExchangeRequest);
        return ApiResponse.noContent();
    }


//    // 전체 게임의 거래소 게시글 목록 조회 (200 OK)
//    @GetMapping
//    public ResponseEntity<ApiResponse<List<ExchangeResponse>>> getExchanges() {
//        List<ExchangeResponse> responses = exchangeService.getExchanges();
//        return ApiResponse.created(responses);
//    }
}
