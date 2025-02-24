package com.example.codePicasso.domain.game.service;

import com.example.codePicasso.domain.game.entity.Game;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface GameConnector {
    public Game save(Game game);

    public List<Game> findAllForUser();

    public List<Game> findAllForAdmin();

    public Game findByIdForUser(Long id);

    public Game findByIdForAdmin(Long gameId);

    public void deleteGameById(Long gameId);
}
