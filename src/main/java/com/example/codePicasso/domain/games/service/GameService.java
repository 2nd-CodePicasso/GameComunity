package com.example.codePicasso.domain.games.service;

import com.example.codePicasso.domain.gameProposal.entity.GameProposal;
import com.example.codePicasso.domain.gameProposal.service.GameProposalConnector;
import com.example.codePicasso.domain.games.dto.request.GameRequest;
import com.example.codePicasso.domain.games.dto.request.UpdateGameRequest;
import com.example.codePicasso.domain.games.dto.response.GameResponse;
import com.example.codePicasso.domain.games.dto.response.GetAllGameResponse;
import com.example.codePicasso.domain.games.entity.Games;
import jakarta.transaction.Transactional;
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

    public GameResponse createGame(GameRequest request) {
        Games game = request.toEntity();

        gameConnector.save(game);

        return game.toDto();
    }

    public GetAllGameResponse getAllGames() {
        List<Games> games = gameConnector.findAll();

        return new GetAllGameResponse(games.stream()
                .map(Games::getGameTitle)
                .toList()
        );
    }

    @Transactional
    public GameResponse updateGame(Long gameId, UpdateGameRequest request) {
        Games foundGame = gameConnector.findById(gameId);
        foundGame.updateDetails(request.gameDescription());

        return foundGame.toDto();
    }
}
