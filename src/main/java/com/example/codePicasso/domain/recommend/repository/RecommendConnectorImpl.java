package com.example.codePicasso.domain.recommend.repository;

import com.example.codePicasso.domain.recommend.entity.Recommend;
import com.example.codePicasso.domain.recommend.service.RecommendConnector;
import com.example.codePicasso.global.exception.base.InvalidRequestException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecommendConnectorImpl implements RecommendConnector {
    private final RecommendRepository recommendRepository;

    @Override
    public Recommend save(Recommend recommend) {
        return recommendRepository.save(recommend);
    }

    @Override
    public Integer countByPostId(Long postId) {
        return recommendRepository.countByPostId(postId);
    }

    @Override
    public Recommend findByPostIdAndUserId(Long postId, Long userId) {
        return recommendRepository.findByPostIdAndUserId(postId, userId)
                .orElseThrow(() -> new InvalidRequestException(ErrorCode.RECOMMEND_NOT_FOUND));
    }

    @Override
    public void delete(Recommend recommend) {
        recommendRepository.delete(recommend);
    }
}
