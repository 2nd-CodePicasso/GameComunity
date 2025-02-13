package com.example.codePicasso.domain.games.service;

import com.example.codePicasso.domain.gameProposal.service.GameProposalConnector;
import com.example.codePicasso.domain.games.dto.request.GameRequest;
import com.example.codePicasso.domain.games.dto.request.UpdateGameRequest;
import com.example.codePicasso.domain.games.dto.response.GameResponse;
import com.example.codePicasso.domain.games.dto.response.GetAllGameResponse;
import com.example.codePicasso.domain.games.entity.Games;
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
        Games game = request.toEntity();

        gameConnector.save(game);
    }

    public List<GetAllGameResponse> getAllGames() {
        return gameConnector.findAll();
    }

    public GameResponse updateGame(Long gameId, UpdateGameRequest request) {
        Games foundGame = gameConnector.findById(gameId);
        foundGame.updateDetails(request.gameDescription());
        gameConnector.save(foundGame);
        return foundGame.toDto();
    }

    public void deleteGame(Long gameId) {
        Games foundGame = gameConnector.findById(gameId);
        foundGame.deleteGame();
        gameConnector.save(foundGame);
    }

    public void restoreGame(Long gameId) {
        Games foundGame = gameConnector.findById(gameId);
        foundGame.restore();
        gameConnector.save(foundGame);
    }
}
