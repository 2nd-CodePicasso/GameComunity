package com.example.codePicasso.domain.game.controller;

import com.example.codePicasso.domain.game.dto.request.UpdateGameRequest;
import com.example.codePicasso.domain.game.dto.response.GameResponse;
import com.example.codePicasso.domain.game.dto.response.GetAllGameResponse;
import com.example.codePicasso.domain.game.service.GameService;
import com.example.codePicasso.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<GetAllGameResponse>>> getAllGamesApi() {
        List<GetAllGameResponse> response = gameService.getAllGames();

        return ApiResponse.success(response);
    }

    @PatchMapping("{gameId}")
    public ResponseEntity<ApiResponse<GameResponse>> updateGameApi(
            @PathVariable Long gameId, @RequestBody UpdateGameRequest request
    ) {
        GameResponse response = gameService.updateGame(gameId, request);

        return ApiResponse.success(response);
    }

    @DeleteMapping("{gameId}")
    public ResponseEntity<ApiResponse<Void>> deleteGameApi(@PathVariable Long gameId) {
        gameService.deleteGame(gameId);

        return ApiResponse.noContentAndSendMessage("게임이 삭제되었습니다.");
    }

    @PatchMapping("/{gameId}/restore")
    public ResponseEntity<ApiResponse<Void>> restoreGameApi(@PathVariable Long gameId) {
        gameService.restoreGame(gameId);

        return ApiResponse.noContentAndSendMessage("게임이 복구되었습니다.");
    }
}
