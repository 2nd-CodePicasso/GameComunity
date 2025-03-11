package com.example.codePicasso.domain.exchange.repository;

import com.example.codePicasso.domain.exchange.entity.TradeCount;
import com.example.codePicasso.domain.exchange.entity.TradeType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeCountRepository extends JpaRepository<TradeCount, Long> {
    Optional<TradeCount> findByGameIdAndTradeType(Long gameId, TradeType tradeType);

    @Query("SELECT tc FROM TradeCount tc WHERE tc.tradeType = :tradeType ORDER BY tc.count DESC")
    List<TradeCount> findTopNByTradeTypeOrderByCountDesc(@Param("tradeType") TradeType tradeType,Pageable pageable);
}
