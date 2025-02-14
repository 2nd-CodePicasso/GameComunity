package com.example.codePicasso.domain.game.service;

import com.example.codePicasso.domain.game.entity.Game;
import com.example.codePicasso.domain.gameProposal.service.GameProposalConnector;
import com.example.codePicasso.domain.game.dto.request.GameRequest;
import com.example.codePicasso.domain.game.dto.request.UpdateGameRequest;
import com.example.codePicasso.domain.game.dto.response.GameResponse;
import com.example.codePicasso.domain.game.dto.response.GetAllGameResponse;
import com.example.codePicasso.global.exception.base.InvalidRequestException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameService {

    private final GameProposalConnector gameProposalConnector;
    private final GameConnector gameConnector;

    public void createGame(GameRequest request) {
        Game game = request.toEntity();

        gameConnector.save(game);
    }

    public List<GetAllGameResponse> getAllGames() {
        return gameConnector.findAll();
    }

    public GameResponse updateGame(Long gameId, UpdateGameRequest request) {
        Game foundGame = gameConnector.findById(gameId);
        foundGame.updateDetails(request.gameDescription());
        gameConnector.save(foundGame);
        return foundGame.toDto();
    }

    public void deleteGame(Long gameId) {
        Game foundGame = gameConnector.findById(gameId);
        validateIsDeleted(foundGame, false, ErrorCode.GAME_ALREADY_DELETED);
        foundGame.deleteGame();
        gameConnector.save(foundGame);
    }

    public void restoreGame(Long gameId) {
        Game foundGame = gameConnector.findById(gameId);
        validateIsDeleted(foundGame, true, ErrorCode.GAME_ALREADY_ACTIVATED);
        foundGame.restore();
        gameConnector.save(foundGame);
    }

    private void validateIsDeleted(Game game, boolean status, ErrorCode errorCode) {
        if (game.isDeleted() != status) {
            throw new InvalidRequestException(errorCode);
        }
    }
}
