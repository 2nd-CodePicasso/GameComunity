package com.example.codePicasso.domain.game.repository;

import com.example.codePicasso.domain.game.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
