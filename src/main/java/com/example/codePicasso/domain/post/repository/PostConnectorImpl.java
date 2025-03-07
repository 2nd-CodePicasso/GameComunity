package com.example.codePicasso.domain.post.repository;

import com.example.codePicasso.domain.post.dto.response.PostSummaryResponse;
import com.example.codePicasso.domain.post.entity.Post;
import com.example.codePicasso.domain.post.enums.PostStatus;
import com.example.codePicasso.domain.post.service.PostConnector;
import com.example.codePicasso.global.exception.base.InvalidRequestException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.example.codePicasso.domain.category.entity.QCategory.category;
import static com.example.codePicasso.domain.game.entity.QGame.game;
import static com.example.codePicasso.domain.post.entity.QPost.post;
import static com.example.codePicasso.domain.user.entity.QUser.user;

@Component
@RequiredArgsConstructor
public class PostConnectorImpl implements PostConnector {
    private final PostRepository postRepository;
    private final JPAQueryFactory queryFactory;

    // 공통 쿼리
    private JPAQuery<Post> basePostQuery() {
        return queryFactory.select(post)
                .from(post)
                .leftJoin(post.user, user).fetchJoin()
                .leftJoin(post.category, category).fetchJoin()
                .leftJoin(post.game, game).fetchJoin();
    }

    // Count 쿼리
    private Long baseCountQuery() {
        return Optional.ofNullable(queryFactory.select(post.count())
                        .from(post)
                        .fetchOne())
                .orElse(0L);
    }

    // 게시글 생성
    @Override
    public Post save(Post post) {

        return postRepository.save(post);
    }

    // gameId로 게시글 전체 조회
    @Override
    public Page<PostSummaryResponse> findAllByGameId(Long gameId, Pageable pageable) {
        List<PostSummaryResponse> posts = queryFactory.select(Projections.constructor(
                PostSummaryResponse.class,
                post.id,
                post.title,
                post.viewCount,
                post.game.gameTitle,
                post.category.categoryName,
                post.user.nickname,
                post.createdAt,
                post.updatedAt
                ))
                .from(post)
                .leftJoin(post.user, user)
                .leftJoin(post.category, category)
                .leftJoin(post.game, game)
                .where(post.game.id.eq(gameId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        if (posts.isEmpty()) {
            throw new InvalidRequestException(ErrorCode.POST_NOT_FOUND);
        }
        Long count = baseCountQuery();

        return new PageImpl<>(posts, pageable, count);
    }

    // 게임별 추천게시물 조회
    @Override
    public Page<Post> findAllRecommendedOfGame(Long gameId, PostStatus postStatus, Pageable pageable) {
        List<Post> posts = basePostQuery()
                .where(post.game.id.eq(gameId), post.status.eq(postStatus))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        if (posts.isEmpty()) {
            throw new InvalidRequestException(ErrorCode.POST_NOT_FOUND);
        }
        Long count = baseCountQuery();

        return new PageImpl<>(posts, pageable, count);
    }

    // categoryId로 게시글 전체 조회
    @Override
    public Page<Post> findAllByCategoryId(Long categoryId, Pageable pageable) {
        List<Post> posts = basePostQuery()
                .where(post.category.id.eq(categoryId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        if (posts.isEmpty()) {
            throw new InvalidRequestException(ErrorCode.POST_NOT_FOUND);
        }
        Long count = baseCountQuery();

        return new PageImpl<>(posts, pageable, count);
    }

    // 게시글 개별 조회
    @Override
    public Post findById(Long postId) {
        Post foundPost = basePostQuery()
                .where(post.id.eq(postId))
                .fetchOne();

        if (foundPost == null) {
            throw new InvalidRequestException(ErrorCode.POST_NOT_FOUND);
        }

        return foundPost;
    }

    // 게시글 수정
    @Override
    public Post findByIdAndUserId(Long postId, Long userId) {
        Post foundPost = basePostQuery()
                .where(post.id.eq(postId))
                .fetchOne();
        if (foundPost == null) {
            throw new InvalidRequestException(ErrorCode.POST_NOT_FOUND);
        }
        // 입력받은 userId와 조회한 userId 검증
        if (!foundPost.getUser().getId().equals(userId)) {
            throw new InvalidRequestException(ErrorCode.UNAUTHORIZED_ID);
        }

        return foundPost;
    }

    // 게시글 삭제
    @Override
    public void delete(Post deletePost) {
        postRepository.delete(deletePost);
    }
}
