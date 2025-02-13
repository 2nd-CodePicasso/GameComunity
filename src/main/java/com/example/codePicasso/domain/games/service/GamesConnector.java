package com.example.codePicasso.domain.games.service;

import com.example.codePicasso.domain.games.entity.Games;
import org.springframework.stereotype.Component;

@Component
public interface GamesConnector {
    Games findById(Long aLong);
}
