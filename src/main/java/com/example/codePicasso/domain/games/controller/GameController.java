package com.example.codePicasso.domain.games.controller;

import com.example.codePicasso.domain.games.dto.request.GameRequest;
import com.example.codePicasso.domain.games.dto.request.UpdateGameRequest;
import com.example.codePicasso.domain.games.dto.response.GameResponse;
import com.example.codePicasso.domain.games.dto.response.GetAllGameResponse;
import com.example.codePicasso.domain.games.service.GameService;
import com.example.codePicasso.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;

    @GetMapping
    public ResponseEntity<ApiResponse<GetAllGameResponse>> getAllGamesApi() {
        GetAllGameResponse response = gameService.getAllGames();

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
}
