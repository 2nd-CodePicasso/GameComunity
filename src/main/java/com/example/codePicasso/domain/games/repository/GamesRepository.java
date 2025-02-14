package com.example.codePicasso.domain.games.repository;

import com.example.codePicasso.domain.games.dto.response.GameResponse;
import com.example.codePicasso.domain.games.dto.response.GetAllGameResponse;
import com.example.codePicasso.domain.games.entity.Games;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GamesRepository extends JpaRepository<Games, Long> {

    @Query("""
            SELECT new com.example.codePicasso.domain.games.dto.response.GetAllGameResponse
            (g.id, g.gameTitle)
            FROM Games g
            """)
    List<GetAllGameResponse> findAllGames();
}
