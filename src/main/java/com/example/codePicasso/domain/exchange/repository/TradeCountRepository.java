package com.example.codePicasso.domain.exchange.repository;

import com.example.codePicasso.domain.exchange.entity.TradeCount;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TradeCountRepository extends JpaRepository<TradeCount, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT e From TradeCount e where e.gameId = :gameId")
    Optional<TradeCount> findByIdForUpdate(@Param("gameId") Long gameId);
}
