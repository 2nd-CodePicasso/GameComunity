package com.example.codePicasso.domain.game.service;

import com.example.codePicasso.domain.game.dto.request.GameRequest;
import com.example.codePicasso.domain.game.dto.request.GameUpdateRequest;
import com.example.codePicasso.domain.game.dto.response.GameGetAllResponse;
import com.example.codePicasso.domain.game.dto.response.GameResponse;
import com.example.codePicasso.domain.game.entity.Game;
import com.example.codePicasso.global.common.DtoFactory;
import com.example.codePicasso.global.exception.base.InvalidRequestException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GameService {

    private final GameConnector gameConnector;

    @Transactional
    public void createGame(GameRequest request) {
        Game game = request.toEntity();

        gameConnector.save(game);
    }

    public GameGetAllResponse getAllGames() {
        return GameGetAllResponse.builder()
                .responses(gameConnector.findAllForUser().stream()
                        .map(DtoFactory::toGameDto).toList())
                .build();
    }

    public GameGetAllResponse getAllGamesIgnoreStatus() {
        return GameGetAllResponse.builder()
                .responses(gameConnector.findAllForAdmin().stream()
                        .map(DtoFactory::toGameDto).toList())
                .build();
    }

    public GameResponse getGame(Long gameId) {
        return DtoFactory.toGameDto(gameConnector.findByIdForUser(gameId));
    }

    public GameResponse getGameIgnoreStatus(Long gameId) {
        return DtoFactory.toGameDto(gameConnector.findByIdForAdmin(gameId));
    }

    @Transactional
    public GameResponse updateGame(Long gameId, GameUpdateRequest request) {
        Game foundGame = gameConnector.findByIdForUser(gameId);
        foundGame.updateDetails(request.gameDescription());
        return DtoFactory.toGameDto(foundGame);
    }

    @Transactional
    public void deleteGame(Long gameId) {
        Game foundGame = gameConnector.findByIdForUser(gameId);
        validateIsDeleted(foundGame, false, ErrorCode.GAME_ALREADY_DELETED);
        foundGame.deleteGame();
    }

    @Transactional
    public void restoreGame(Long gameId) {
        Game foundGame = gameConnector.findByIdForUser(gameId);
        validateIsDeleted(foundGame, true, ErrorCode.GAME_ALREADY_ACTIVATED);
        foundGame.restore();
    }

    private void validateIsDeleted(Game game, boolean status, ErrorCode errorCode) {
        if (game.isDeleted() != status) {
            throw new InvalidRequestException(errorCode);
        }
    }
}
