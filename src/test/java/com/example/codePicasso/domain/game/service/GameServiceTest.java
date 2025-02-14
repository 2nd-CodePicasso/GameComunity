package com.example.codePicasso.domain.game.service;

import com.example.codePicasso.domain.game.dto.request.UpdateGameRequest;
import com.example.codePicasso.domain.game.dto.response.GameResponse;
import com.example.codePicasso.domain.game.entity.Game;
import com.example.codePicasso.global.exception.base.InvalidRequestException;
import com.example.codePicasso.global.exception.base.NotFoundException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private GameConnector gameConnector;

    @InjectMocks
    private GameService gameService;

    private Game mockGame;

    @BeforeEach
    void setUp() {
        mockGame = spy(new Game(1L, null, "Test Game", "Initial Description", false));
        lenient().when(gameConnector.findById(1L)).thenReturn(mockGame);
//        when(gameConnector.findAll()).thenReturn(List.of(new GetAllGameResponse(1L, "Test Game")));
    }

    // ✅성공 케이스
    @Test
    void 게임_설명_업데이트() {
        // Given
        Long gameId = 1L;
        String newDescription = "Updated Description";
        UpdateGameRequest request = new UpdateGameRequest(newDescription);

        // When
        GameResponse response = gameService.updateGame(gameId, request);

        // Then
        assertEquals(newDescription, response.gameDescription());
        verify(gameConnector, times(1)).save(mockGame);
    }

    @Test
    void 게임_삭제() {
        // Given
        Long gameId = 1L;

        // When
        gameService. deleteGame(gameId);

        // Then
        assertTrue(mockGame.isDeleted());
        verify(gameConnector, times(1)).save(mockGame);
    }

    @Test
    void 게임_복구() {
        // Given
        Long gameId = 1L;
        gameService. deleteGame(gameId);

        // When
        gameService.restoreGame(gameId);

        // Then
        assertFalse(mockGame.isDeleted());
        verify(gameConnector, times(2)).save(mockGame);
    }


    // ❌실패 케이스
    @Test
    void 존재하지_않는_게임아이디로_조회() {
        // Given
        Long gameId = 999L;
        when(gameConnector.findById(gameId))
                .thenThrow(new NotFoundException(ErrorCode.GAME_NOT_FOUND));

        // When & Then
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> gameService.updateGame(gameId,
                        new UpdateGameRequest("Updated Description")));
        assertEquals(ErrorCode.GAME_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void 삭제된_게임_삭제_시도() {
        // Given
        Long gameId = 1L;
        gameService.deleteGame(1L);

        // When & Then
        InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                () -> gameService.deleteGame(gameId));
        assertEquals(ErrorCode.GAME_ALREADY_DELETED, exception.getErrorCode());
        verify(gameConnector, times(1)).save(mockGame);
    }

    @Test
    void 활성_상태인_게임_복구_시도() {
        // Given
        Long gameId = 1L;

        // When & Then
        InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                () -> gameService.restoreGame(gameId));
        assertEquals(ErrorCode.GAME_ALREADY_ACTIVATED, exception.getErrorCode());
        verify(gameConnector, times(0)).save(mockGame);
    }
}