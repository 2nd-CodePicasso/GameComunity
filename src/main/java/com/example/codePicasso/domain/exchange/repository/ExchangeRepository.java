package com.example.codePicasso.domain.exchange.repository;

import com.example.codePicasso.domain.exchange.entity.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeRepository extends JpaRepository<Exchange, Long> {
}