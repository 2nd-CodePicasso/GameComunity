package com.example.codePicasso.domain.exchanges.repository;

import com.example.codePicasso.domain.exchanges.entity.ItemExchange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeRepository extends JpaRepository<ItemExchange, Long> {
}
