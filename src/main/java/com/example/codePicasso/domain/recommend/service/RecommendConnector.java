package com.example.codePicasso.domain.recommend.service;

import com.example.codePicasso.domain.recommend.entity.Recommend;
import org.springframework.stereotype.Component;

@Component
public interface RecommendConnector {
    Recommend save(Recommend recommend);
}
