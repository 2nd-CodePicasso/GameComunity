package com.example.codePicasso.domain.games.repository;

import com.example.codePicasso.domain.games.dto.response.GetAllGameResponse;
import com.example.codePicasso.domain.games.entity.Games;
import com.example.codePicasso.domain.games.service.GameConnector;
import com.example.codePicasso.global.exception.base.NotFoundException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GameConnectorImpl implements GameConnector {

    private final GamesRepository gamesRepository;

    @Override
    public void save(Games game) {
        gamesRepository.save(game);
    }

    @Override
    public List<GetAllGameResponse> findAll() {
        return gamesRepository.findAllGames();
    }

    @Override
    public Games findById(Long gameId) {
        return gamesRepository.findById(gameId).orElseThrow(
                () -> new NotFoundException(ErrorCode.GAME_NOT_FOUND)
        );
    }
}
