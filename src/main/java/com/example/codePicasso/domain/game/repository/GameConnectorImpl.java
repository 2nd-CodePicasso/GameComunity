package com.example.codePicasso.domain.game.repository;

import com.example.codePicasso.domain.game.entity.Game;
import com.example.codePicasso.domain.game.service.GameConnector;
import com.example.codePicasso.global.exception.base.NotFoundException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GameConnectorImpl implements GameConnector {
    private final GameRepository gameRepository;

    @Override
    public Game save(Game game) {
        return gameRepository.save(game);
    }

    @Override
    public List<Game> findAllForUser() {
        return gameRepository.findAllByDeleted(false);
    }

    @Override
    public List<Game> findAllForAdmin() {
        return gameRepository.findAll();
    }

    @Override
    public Game findByIdForUser(Long gameId) {
        Game foundGame = gameRepository.findById(gameId).orElseThrow(
                () -> new NotFoundException(ErrorCode.GAME_NOT_FOUND));

        if (foundGame.isDeleted()) {
            throw new NotFoundException(ErrorCode.GAME_ALREADY_DELETED);
        }

        return foundGame;
    }

    @Override
    public Game findByIdForAdmin(Long gameId) {
        return gameRepository.findById(gameId).orElseThrow(
                () -> new NotFoundException(ErrorCode.GAME_NOT_FOUND)
        );
    }

    @Override
    public void deleteGameById(Long gameId) {
        Game foundGame = gameRepository.findById(gameId).orElseThrow(
                () -> new NotFoundException(ErrorCode.GAME_NOT_FOUND)
        );
        gameRepository.delete(foundGame);
    }
}
