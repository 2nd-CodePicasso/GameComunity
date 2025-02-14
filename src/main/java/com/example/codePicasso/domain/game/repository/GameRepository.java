package com.example.codePicasso.domain.game.repository;

import com.example.codePicasso.domain.game.dto.response.GetAllGameResponse;
import com.example.codePicasso.domain.game.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {

    @Query("""
            SELECT new com.example.codePicasso.domain.game.dto.response.GetAllGameResponse
            (g.id, g.gameTitle)
            FROM Game g
            """)
    List<GetAllGameResponse> findAllGames();
}
