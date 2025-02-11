package com.example.codePicasso.domain.games.repository;

import com.example.codePicasso.domain.games.entity.Games;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GamesRepository extends JpaRepository<Games, Long> {
}
