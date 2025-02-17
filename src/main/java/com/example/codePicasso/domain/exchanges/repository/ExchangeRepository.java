package com.example.codePicasso.domain.exchanges.repository;

import com.example.codePicasso.domain.exchanges.entity.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExchangeRepository extends JpaRepository<Exchange, Long> {

    List<Exchange> findByGameId(Long gameId);

}
