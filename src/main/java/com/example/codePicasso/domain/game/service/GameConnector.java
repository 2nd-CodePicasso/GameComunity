package com.example.codePicasso.domain.game.service;

import com.example.codePicasso.domain.game.entity.Game;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface GameConnector {
    Game save(Game game);

    List<Game> findAllForUser();

    List<Game> findAllForAdmin();

    Game findByIdForUser(Long id);

    Game findByIdForAdmin(Long gameId);

    void deleteGameById(Long gameId);
}
