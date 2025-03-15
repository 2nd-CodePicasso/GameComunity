package com.example.codePicasso.domain.post.repository;

import com.example.codePicasso.domain.post.dto.response.PostResponse;
import com.example.codePicasso.domain.post.dto.response.QPostResponse;
import com.example.codePicasso.domain.post.entity.Post;
import com.example.codePicasso.domain.post.enums.PostStatus;
import com.example.codePicasso.domain.post.service.PostConnector;
import com.example.codePicasso.global.exception.base.InvalidRequestException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.codePicasso.domain.category.entity.QCategory.category;
import static com.example.codePicasso.domain.game.entity.QGame.game;
import static com.example.codePicasso.domain.post.entity.QPost.post;
import static com.example.codePicasso.domain.user.entity.QUser.user;

@Component
@RequiredArgsConstructor
public class PostConnectorImpl implements PostConnector {
    private final PostRepository postRepository;
    private final JPAQueryFactory queryFactory;

    // 게시글 생성
    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    // gameId로 게시글 전체 조회
    @Override
    public Page<PostResponse> findAllByGameId(Long gameId, Pageable pageable) {
        List<PostResponse> posts = queryFactory.select(new QPostResponse(
                        post.id,
                        post.game.id,
                        post.category.id,
                        post.user.id,
                        post.category.categoryName,
                        post.title,
                        post.user.nickname,
                        post.description,
                        post.viewCount,
                        post.status,
                        post.createdAt,
                        post.updatedAt))
                .from(post)
                .join(post.user, user)
                .join(post.category, category)
                .join(post.game, game)
                .where(post.game.id.eq(gameId))
                .orderBy(post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        if (posts.isEmpty()) {
            throw new InvalidRequestException(ErrorCode.POST_NOT_FOUND);
        }

        JPAQuery<Long> count = queryFactory.select(post.count())
                .from(post)
                .where(post.game.id.eq(gameId));

        return PageableExecutionUtils.getPage(posts, pageable, count::fetchOne);
    }

    // 게임별 추천게시물 조회
    @Override
    public Page<PostResponse> findAllRecommendedOfGame(Long gameId, PostStatus postStatus, Pageable pageable) {
        List<PostResponse> posts = queryFactory.select(new QPostResponse(
                        post.id,
                        post.game.id,
                        post.category.id,
                        post.user.id,
                        post.category.categoryName,
                        post.title,
                        post.user.nickname,
                        post.description,
                        post.viewCount,
                        post.status,
                        post.createdAt,
                        post.updatedAt))
                .from(post)
                .join(post.user, user)
                .join(post.category, category)
                .join(post.game, game)
                .where(post.game.id.eq(gameId), post.status.eq(postStatus))
                .orderBy(post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        if (posts.isEmpty()) {
            throw new InvalidRequestException(ErrorCode.POST_NOT_FOUND);
        }

        JPAQuery<Long> count = queryFactory.select(post.count())
                .from(post)
                .where(post.game.id.eq(gameId), post.status.eq(postStatus));

        return PageableExecutionUtils.getPage(posts, pageable, count::fetchOne);
    }

    // categoryId로 게시글 전체 조회
    @Override
    public Page<PostResponse> findAllByCategoryId(Long categoryId, Pageable pageable) {
        List<PostResponse> posts = queryFactory.select(new QPostResponse(
                        post.id,
                        post.game.id,
                        post.category.id,
                        post.user.id,
                        post.category.categoryName,
                        post.title,
                        post.user.nickname,
                        post.description,
                        post.viewCount,
                        post.status,
                        post.createdAt,
                        post.updatedAt))
                .from(post)
                .join(post.user, user)
                .join(post.category, category)
                .join(post.game, game)
                .where(post.category.id.eq(categoryId))
                .orderBy(post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        if (posts.isEmpty()) {
            throw new InvalidRequestException(ErrorCode.POST_NOT_FOUND);
        }

        JPAQuery<Long> count = queryFactory.select(post.count())
                .from(post)
                .where(post.category.id.eq(categoryId));

        return PageableExecutionUtils.getPage(posts, pageable, count::fetchOne);
    }

    // 게시글 개별 조회
    @Override
    public Post findById(Long postId) {
        Post foundPost = queryFactory.select(post)
                .from(post)
                .leftJoin(post.user, user).fetchJoin()
                .leftJoin(post.category, category).fetchJoin()
                .leftJoin(post.game, game).fetchJoin()
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
        Post foundPost = queryFactory.select(post)
                .from(post)
                .leftJoin(post.user, user).fetchJoin()
                .leftJoin(post.category, category).fetchJoin()
                .leftJoin(post.game, game).fetchJoin()
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

    @Override
    public List<PostResponse> findByRecentPost(int size, int page) {
        return queryFactory.select(new QPostResponse(
                        post.id,
                        post.game.id,
                        post.category.id,
                        post.user.id,
                        post.category.categoryName,
                        post.title,
                        post.user.nickname,
                        post.description,
                        post.viewCount,
                        post.status,
                        post.createdAt,
                        post.updatedAt))
                .from(post)
                .join(post.user, user)
                .join(post.category, category)
                .join(post.game, game)
                .where(post.status.eq(PostStatus.RECOMMENDED))
                .orderBy(post.viewCount.desc())
                .offset(0)
                .limit(size)
                .fetch()
        ;
    }
}
