package com.example.codePicasso.domain.recommend.service;

import com.example.codePicasso.domain.post.entity.Post;
import com.example.codePicasso.domain.post.service.PostConnector;
import com.example.codePicasso.domain.recommend.dto.response.RecommendResponse;
import com.example.codePicasso.domain.recommend.entity.Recommend;
import com.example.codePicasso.domain.user.entity.User;
import com.example.codePicasso.domain.user.service.UserConnector;
import com.example.codePicasso.global.common.DtoFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecommendService {
        private final PostConnector postConnector;
        private final UserConnector userConnector;
        private final RecommendConnector recommendConnector;

        @Transactional
        public RecommendResponse doRecommend(Long postId, Long userId) {
            User foundUser = userConnector.findById(userId);
            Post foundPost = postConnector.findById(postId);

            Recommend recommend = Recommend.builder()
                    .user(foundUser)
                    .post(foundPost)
                    .build();

            Recommend save = recommendConnector.save(recommend);
            return DtoFactory.toRecommendDto(save);
        }

        public Integer countRecommendOfPost(Long postId) {
            return recommendConnector.countByPostId(postId);
        }

        public void undoRecommend(Long postId, Long userId) {
            Recommend recommend = recommendConnector.findByPostIdAndUserId(postId, userId);

            recommendConnector.delete(recommend);
        }

}
