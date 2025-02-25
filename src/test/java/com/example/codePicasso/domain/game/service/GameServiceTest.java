package com.example.codePicasso.domain.game.service;

import com.example.codePicasso.domain.game.dto.request.GameRequest;
import com.example.codePicasso.domain.game.dto.request.GameUpdateRequest;
import com.example.codePicasso.domain.game.dto.response.GameGetAllResponse;
import com.example.codePicasso.domain.game.dto.response.GameResponse;
import com.example.codePicasso.domain.game.entity.Game;
import com.example.codePicasso.domain.gameProposal.entity.GameProposal;
import com.example.codePicasso.domain.gameProposal.enums.ProposalStatus;
import com.example.codePicasso.domain.user.entity.Admin;
import com.example.codePicasso.domain.user.entity.User;
import com.example.codePicasso.global.exception.base.InvalidRequestException;
import com.example.codePicasso.global.exception.base.NotFoundException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private GameConnector gameConnector;

    @InjectMocks
    private GameService gameService;

    LocalDateTime fixedDateTime;
    private User mockUser1;
    private Admin mockAdmin1;
    private GameProposal mockProposal1;
    private Game mockGame1;
    private Game mockGame2;
    private Game mockGame3;


    @BeforeEach
    void setUp() {
        lenient().when(gameConnector.findByIdForUser(1L)).thenReturn(mockGame1);
        fixedDateTime = LocalDateTime.of(2024, 2, 25, 12, 0, 0);
        mockUser1 = spy(new User("user1", "testUser1", "user123"));
        mockAdmin1 = spy(new Admin("admin1", "admin123"));
        mockProposal1 = GameProposal.builder()
                .id(1L)
                .user(mockUser1)
                .gameTitle("Test Game1")
                .status(ProposalStatus.WAITING)
                .build();

        mockGame1 = spy(Game.builder()
                .id(1L)
                .admin(mockAdmin1)
                .gameTitle("Test Game1")
                .gameDescription("Test Description1")
                .isDeleted(false)
                .build());

        mockGame2 = spy(Game.builder()
                .id(2L)
                .admin(mockAdmin1)
                .gameTitle("Test Game2")
                .gameDescription("Test Description2")
                .isDeleted(true)
                .build());

        mockGame3 = spy(Game.builder()
                .id(3L)
                .admin(mockAdmin1)
                .gameTitle("Test Game3")
                .gameDescription("Test Description3")
                .isDeleted(false)
                .build());


        lenient().doReturn(fixedDateTime).when(mockGame1).getCreatedAt();
        lenient().doReturn(fixedDateTime).when(mockGame1).getUpdatedAt();
        lenient().doReturn(fixedDateTime).when(mockGame2).getCreatedAt();
        lenient().doReturn(fixedDateTime).when(mockGame2).getUpdatedAt();
        lenient().doReturn(fixedDateTime).when(mockGame3).getCreatedAt();
        lenient().doReturn(fixedDateTime).when(mockGame3).getUpdatedAt();
        //        when(gameConnector.findAll()).thenReturn(List.of(new GameGetAllResponse(1L, "Test Game")));
    }

    // ✅성공 케이스
    @Test
    void 게임_생성_성공() {
        // GIVEN
        GameRequest request = new GameRequest(mockProposal1);

        // WHEN
        gameService.createGame(request);

        // THEN
        verify(gameConnector, times(1)).save(any(Game.class));
    }

    @Test
    void 유저가_모든_게임_조회() {
        // GIVEN
        when(gameConnector.findAllForUser())
                .thenReturn(List.of(mockGame1, mockGame3));

        // WHEN
        GameGetAllResponse response = gameService.getAllGames();

        // THEN
        assertEquals(mockGame1.getGameTitle(), response.responses().get(0).gameTitle());
        assertEquals(mockGame3.getGameTitle(), response.responses().get(1).gameTitle());
    }

    @Test
    void 관리자가_모든_게임_조회() {
        // GIVEN
        when(gameConnector.findAllForAdmin())
                .thenReturn(List.of(mockGame1, mockGame2, mockGame3));

        // WHEN
        GameGetAllResponse response = gameService.getAllGamesIgnoreStatus();

        // THEN
        assertEquals(mockGame1.getGameTitle(), response.responses().get(0).gameTitle());
        assertEquals(mockGame2.getGameTitle(), response.responses().get(1).gameTitle());
        assertEquals(mockGame3.getGameTitle(), response.responses().get(2).gameTitle());
    }

    @Test
    void 유저가_활성화된_게임_단건_조회() {
        // GIVEN
        Long gameId = 1L;
        when(gameConnector.findByIdForUser(gameId)).thenReturn(mockGame1);

        // WHEN
        GameResponse response = gameService.getGame(gameId);

        // THEN
        assertEquals(mockGame1.getGameTitle(), response.gameTitle());
    }

    @Test
    void 관리자가_삭제된_게임_단건_조회() {
        // GIVEN
        Long gameId = 2L;
        when(gameConnector.findByIdForAdmin(gameId)).thenReturn(mockGame2);

        // WHEN
        GameResponse response = gameService.getGameIgnoreStatus(gameId);

        // THEN
        assertEquals(mockGame1.getGameTitle(), response.gameTitle());
    }

    @Test
    void 게임_설명_업데이트() {
        // Given
        Long gameId = 1L;
        String newDescription = "Updated Description";
        GameUpdateRequest request = new GameUpdateRequest(newDescription);
        when(gameConnector.findByIdForUser(gameId)).thenReturn(mockGame1);

        // When
        GameResponse response = gameService.updateGame(gameId, request);

        // Then
        verify(gameConnector).findByIdForUser(1L);
        assertEquals(newDescription, response.gameDescription());
    }

    @Test
    void 게임_삭제() {
        // Given
        Long gameId = 1L;
        when(gameConnector.findByIdForUser(gameId)).thenReturn(mockGame1);

        // When
        gameService.softDeleteGame(gameId);

        // Then
        assertTrue(mockGame1.isDeleted());
    }

    @Test
    void 게임_복구() {
        // Given
        Long gameId = 1L;
        when(gameConnector.findByIdForUser(gameId)).thenReturn(mockGame1);
        gameService.softDeleteGame(gameId);

        // When
        gameService.restoreGame(gameId);

        // Then
        assertFalse(mockGame1.isDeleted());
    }

    @Test
    void 게임_하드_딜리트_성공() {
        // GIVEN
        Long gameId = 1L;

        // WHEN
        gameService.hardDeleteGame(gameId);

        // THEN
        verify(gameConnector, times(1)).deleteGameById(gameId);
    }

    // ❌실패 케이스
    @Test
    void 존재하지_않는_게임아이디로_조회() {
        // Given
        Long gameId = 999L;
        when(gameConnector.findByIdForUser(gameId))
                .thenThrow(new NotFoundException(ErrorCode.GAME_NOT_FOUND));

        // When & Then
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> gameService.updateGame(gameId,
                        new GameUpdateRequest("Updated Description")));
        assertEquals(ErrorCode.GAME_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void 삭제된_게임_삭제_시도() {
        // Given
        Long gameId = 1L;
        when(gameConnector.findByIdForUser(gameId)).thenReturn(mockGame1);
        gameService.softDeleteGame(1L);

        // When & Then
        InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                () -> gameService.softDeleteGame(gameId));
        assertEquals(ErrorCode.GAME_ALREADY_DELETED, exception.getErrorCode());
    }

    @Test
    void 활성_상태인_게임_복구_시도() {
        // Given
        Long gameId = 1L;
        when(gameConnector.findByIdForUser(gameId)).thenReturn(mockGame1);

        // When & Then
        InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                () -> gameService.restoreGame(gameId));
        assertEquals(ErrorCode.GAME_ALREADY_ACTIVATED, exception.getErrorCode());
        verify(gameConnector, times(0)).save(mockGame1);
    }

    @Test
    void 존재하지_않는_게임_하드_딜리트_실패() {
        // GIVEN
        Long gameId = 999L;
        doThrow(new NotFoundException(ErrorCode.GAME_NOT_FOUND))
                .when(gameConnector).deleteGameById(gameId);

        // WHEN & THEN
        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                gameService.hardDeleteGame(gameId)
        );
        assertEquals(ErrorCode.GAME_NOT_FOUND, exception.getErrorCode());
    }
}