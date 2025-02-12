package com.example.codePicasso.domain.games.service;

import com.example.codePicasso.domain.games.entity.Games;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface GameConnector {
    public void save(Games game);

    public List<Games> findAll();

    public Games findById(Long gameId);
}
