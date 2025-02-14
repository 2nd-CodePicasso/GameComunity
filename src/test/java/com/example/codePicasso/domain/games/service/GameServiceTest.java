package com.example.codePicasso.domain.games.service;

import com.example.codePicasso.domain.games.dto.request.UpdateGameRequest;
import com.example.codePicasso.domain.games.dto.response.GameResponse;
import com.example.codePicasso.domain.games.dto.response.GetAllGameResponse;
import com.example.codePicasso.domain.games.entity.Games;
import com.example.codePicasso.global.exception.base.NotFoundException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private GameConnector gameConnector;

    @InjectMocks
    private GameService gameService;

    private Games mockGame;

    @BeforeEach
    void setUp() {
        mockGame = spy(new Games(1L, null, "Test Game", "Initial Description", false));
        when(gameConnector.findById(1L)).thenReturn(mockGame);
//        when(gameConnector.findById(2L)).thenThrow(new NotFoundException(ErrorCode.GAME_NOT_FOUND));
//        when(gameConnector.findAll()).thenReturn(List.of(new GetAllGameResponse(1L, "Test Game")));
    }

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
}