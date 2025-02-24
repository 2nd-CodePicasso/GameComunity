package com.example.codePicasso.domain.recommend.service;

import com.example.codePicasso.domain.post.entity.Post;
import com.example.codePicasso.domain.post.service.PostConnector;
import com.example.codePicasso.domain.recommend.entity.Recommend;
import com.example.codePicasso.domain.user.entity.User;
import com.example.codePicasso.domain.user.service.UserConnector;
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
    public Integer doRecommend(Long postId, Long userId) {
        recommendConnector.checkExist(postId, userId);

        User foundUser = userConnector.findById(userId);
        Post foundPost = postConnector.findById(postId);

        Recommend recommend = Recommend.builder()
                .user(foundUser)
                .post(foundPost)
                .build();

        recommendConnector.save(recommend);

        if (recommendConnector.countByPostId(postId) >= 10) {
            foundPost.changeStatusToRecommended();
        }
        return recommendConnector.countByPostId(postId);
    }

    public Integer countRecommendOfPost(Long postId) {
        return recommendConnector.countByPostId(postId);
    }

    @Transactional
    public void undoRecommend(Long postId, Long userId) {
        Recommend recommend = recommendConnector.findByPostIdAndUserId(postId, userId);

        recommendConnector.delete(recommend);
    }

}
