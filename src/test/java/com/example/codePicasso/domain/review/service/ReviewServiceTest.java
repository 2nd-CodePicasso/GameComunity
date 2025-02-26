package com.example.codePicasso.domain.review.service;

import com.example.codePicasso.domain.exchange.entity.MyExchange;
import com.example.codePicasso.domain.exchange.entity.StatusType;
import com.example.codePicasso.domain.exchange.service.MyExchangeConnector;
import com.example.codePicasso.domain.review.dto.request.ReviewRequest;
import com.example.codePicasso.domain.review.dto.response.ReviewResponse;
import com.example.codePicasso.domain.review.entity.Review;
import com.example.codePicasso.global.common.DtoFactory;
import com.example.codePicasso.global.exception.base.InvalidRequestException;
import com.example.codePicasso.global.exception.base.NotFoundException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewConnector reviewConnector;

    @Mock
    private MyExchangeConnector myExchangeConnector;

    @InjectMocks
    private ReviewService reviewService;

    private Long exchangeId = 1L;
    private Long userId = 1L;
    private Long reviewId = 1L;
    private ReviewRequest reviewRequest;
    private Review review;
    private MyExchange myExchange;
    private Pageable pageable = PageRequest.of(0, 10);

    @BeforeEach
    void setUp() {
        myExchange = mock(MyExchange.class);
        reviewRequest = new ReviewRequest(5, "Excellent service!");
        review = mock(Review.class);
    }

    /// --- Review ✅ ---
    @Test
    void 리뷰_생성() {
        // given
        when(myExchangeConnector.findByExchangeIdAndUserId(exchangeId, userId)).thenReturn(myExchange);
        when(myExchange.getStatusType()).thenReturn(StatusType.COMPLETED);
        when(reviewRequest.toEntity(any(), any())).thenReturn(review);
        when(reviewConnector.save(any(Review.class))).thenReturn(review);
        when(DtoFactory.toReviewDto(review)).thenReturn(new ReviewResponse(reviewId, 5, "Excellent service!"));

        // when
        ReviewResponse response = reviewService.createReview(exchangeId, userId, reviewRequest);

        // then
        assertNotNull(response);
        assertEquals(5, response.rating());
    }

    @Test
    void 리뷰_조회() {
        // given
        Page<Review> reviews = new PageImpl<>(List.of(review));
        when(reviewConnector.findAllByExchangeId(exchangeId, pageable)).thenReturn(reviews);
        when(DtoFactory.toReviewDto(any())).thenReturn(new ReviewResponse(reviewId, 5, "Excellent service!"));

        // when
        Page<ReviewResponse> responses = reviewService.getReviews(exchangeId, 0, 10);

        // then
        assertNotNull(responses);
        assertEquals(1, responses.getSize());
    }

    @Test
    void 리뷰_수정() {
        // given
        when(reviewConnector.findById(reviewId)).thenReturn(review);
        when(review.getExchange().getId()).thenReturn(exchangeId);
        when(review.getUser().getId()).thenReturn(userId);
        when(DtoFactory.toReviewDto(review)).thenReturn(new ReviewResponse(reviewId, exchangeId, 4, "Updated review"));

        // when
        ReviewResponse response = reviewService.updateReview(exchangeId, reviewId, userId, new ReviewRequest(4, "Updated review"));

        // then
        assertNotNull(response);
        assertEquals(4, response.rating());
        assertEquals("Updated review", response.review());
    }

    @Test
    void 리뷰_삭제() {
        // given
        when(reviewConnector.findById(reviewId)).thenReturn(review);
        when(review.getExchange().getId()).thenReturn(exchangeId);
        when(review.getUser().getId()).thenReturn(userId);

        // when
        reviewService.deleteReview(exchangeId, reviewId, userId);

        // then
        verify(reviewConnector).delete(review);
    }
    /// --- Review ✅ ---

    /// --- Review ❌ ---
    @Test
    void 리뷰_생성_실패_거래완료되지않음() {
        // given
        when(myExchangeConnector.findByExchangeIdAndUserId(exchangeId, userId)).thenReturn(myExchange);
        when(myExchange.getStatusType()).thenReturn(StatusType.PROGRESS);

        // when & then
        InvalidRequestException exception = assertThrows(InvalidRequestException.class, () -> {
            reviewService.createReview(exchangeId, userId, reviewRequest);
        });

        assertEquals(ErrorCode.NOT_COMPLETED, exception.getErrorCode());
    }

    @Test
    void 리뷰_조회_실패_잘못된_거래_ID() {
        // given
        when(reviewConnector.findById(reviewId)).thenReturn(review);
        when(review.getExchange().getId()).thenReturn(2L);

        // when & then
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            reviewService.getReviewById(exchangeId, reviewId);
        });

        assertEquals(ErrorCode.EXCHANGE_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void 리뷰_삭제_실패_사용자_ID_불일치() {
        // given
        when(reviewConnector.findById(reviewId)).thenReturn(review);
        when(review.getExchange().getId()).thenReturn(exchangeId);
        when(review.getUser().getId()).thenReturn(2L);

        // when & then
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            reviewService.deleteReview(exchangeId, reviewId, userId);
        });

        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
    }
    /// --- Review ❌ ---
}
