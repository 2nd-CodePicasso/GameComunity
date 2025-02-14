package com.example.codePicasso.domain.game.repository;

import com.example.codePicasso.domain.game.dto.response.GetAllGameResponse;
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
    public void save(Game game) {
        gameRepository.save(game);
    }

    @Override
    public List<GetAllGameResponse> findAll() {
        return gameRepository.findAllGames();
    }

    @Override
    public Game findById(Long gameId) {
        return gameRepository.findById(gameId).orElseThrow(
                () -> new NotFoundException(ErrorCode.GAME_NOT_FOUND)
        );
    }
}
