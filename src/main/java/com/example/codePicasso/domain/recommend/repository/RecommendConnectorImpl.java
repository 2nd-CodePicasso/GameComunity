package com.example.codePicasso.domain.recommend.repository;

import com.example.codePicasso.domain.recommend.service.RecommendConnector;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecommendConnectorImpl implements RecommendConnector {
    private final RecommendRepository recommendRepository;
}
