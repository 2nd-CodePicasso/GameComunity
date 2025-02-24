package com.example.codePicasso.domain.game.controller;

import com.example.codePicasso.domain.game.dto.request.GameUpdateRequest;
import com.example.codePicasso.domain.game.dto.response.GameGetAllResponse;
import com.example.codePicasso.domain.game.dto.response.GameResponse;
import com.example.codePicasso.domain.game.service.GameService;
import com.example.codePicasso.global.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;

    @GetMapping
    public ResponseEntity<ApiResponse<GameGetAllResponse>> getAllGames() {
        GameGetAllResponse response = gameService.getAllGames();

        return ApiResponse.success(response);
    }

    @GetMapping("/admin")
    public ResponseEntity<ApiResponse<GameGetAllResponse>> getGamesIgnoreStatus() {
        GameGetAllResponse response = gameService.getAllGamesIgnoreStatus();

        return ApiResponse.success(response);
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<ApiResponse<GameResponse>> getGameById(
            @PathVariable("gameId") Long gameId) {
        GameResponse response = gameService.getGame(gameId);

        return ApiResponse.success(response);
    }

    @GetMapping("/admin/{gameId}")
    public ResponseEntity<ApiResponse<GameResponse>> getGameByIdIgnoreStatus(
            @PathVariable Long gameId
    ) {
        GameResponse response = gameService.getGameIgnoreStatus(gameId);

        return ApiResponse.success(response);
    }

    @PatchMapping("/admin/{gameId}")
    public ResponseEntity<ApiResponse<GameResponse>> updateGame(
            @PathVariable Long gameId,
            @Valid @RequestBody GameUpdateRequest request
    ) {
        GameResponse response = gameService.updateGame(gameId, request);
        return ApiResponse.success(response);
    }

    @DeleteMapping("/admin/{gameId}/delete")
    public ResponseEntity<ApiResponse<Void>> deleteGame(
            @PathVariable Long gameId
    ) {
        gameService.deleteGame(gameId);

        return ApiResponse.noContentAndSendMessage("게임이 삭제되었습니다.");
    }

    @PatchMapping("/admin/{gameId}/restore")
    public ResponseEntity<ApiResponse<Void>> restoreGame(
            @PathVariable Long gameId
    ) {
        gameService.restoreGame(gameId);

        return ApiResponse.noContentAndSendMessage("게임이 복구되었습니다.");
    }
}
