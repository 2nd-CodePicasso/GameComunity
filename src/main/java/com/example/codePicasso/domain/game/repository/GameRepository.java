package com.example.codePicasso.domain.game.repository;

import com.example.codePicasso.domain.game.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findAllByIsDeleted(boolean isDeleted);
}
