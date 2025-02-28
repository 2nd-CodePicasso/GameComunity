package com.example.codePicasso.domain.gameProposal.service;

import com.example.codePicasso.domain.game.service.GameService;
import com.example.codePicasso.domain.gameProposal.dto.request.CreateGameProposalRequest;
import com.example.codePicasso.domain.gameProposal.dto.request.ReviewGameProposalRequest;
import com.example.codePicasso.domain.gameProposal.dto.response.GameProposalGetManyResponse;
import com.example.codePicasso.domain.gameProposal.dto.response.GameProposalResponse;
import com.example.codePicasso.domain.gameProposal.entity.GameProposal;
import com.example.codePicasso.domain.gameProposal.enums.ProposalStatus;
import com.example.codePicasso.domain.user.entity.Admin;
import com.example.codePicasso.domain.user.entity.User;
import com.example.codePicasso.domain.user.service.AdminConnector;
import com.example.codePicasso.domain.user.service.UserConnector;
import com.example.codePicasso.global.exception.base.AccessDeniedException;
import com.example.codePicasso.global.exception.base.ConflictException;
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
class GameProposalServiceTest {

    @InjectMocks
    private GameProposalService gameProposalService;

    @Mock
    private GameProposalConnector gameProposalConnector;

    @Mock
    private UserConnector userConnector;

    @Mock
    private AdminConnector adminConnector;

    @Mock
    private GameService gameService;

    private User mockUser1;
    private User mockUser2;
    private User mockUser3;
    private Admin mockAdmin;
    private GameProposal mockProposal1;
    private GameProposal mockProposal2;
    private GameProposal mockProposal3;
    private GameProposal mockProposal4;
    private GameProposal mockProposal5;
    private GameProposal mockChangeProposal;

    @BeforeEach
    void setUp() {
        mockUser1 = spy(new User("user1", "testUser1", "user123"));
        mockUser2 = spy(new User("user2", "testUser2", "user123"));
        mockUser3 = spy(new User("user3", "testUser3", "user123"));
        mockAdmin = new Admin("admin", "testAdmin");
        mockProposal1 = GameProposal.builder()
                .id(1L)
                .user(mockUser1)
                .gameTitle("Test Game1")
                .status(ProposalStatus.WAITING)
                .build();

        mockProposal2 = GameProposal.builder()
                .id(2L)
                .user(mockUser1)
                .gameTitle("Test Game1")
                .status(ProposalStatus.REJECTED)
                .build();

        mockProposal3 = GameProposal.builder()
                .id(3L)
                .user(mockUser2)
                .admin(mockAdmin)
                .gameTitle("Test Game2")
                .status(ProposalStatus.APPROVED)
                .build();

        mockProposal4 = GameProposal.builder()
                .id(4L)
                .user(mockUser2)
                .admin(mockAdmin)
                .gameTitle("Test Game STRANGE")
                .status(ProposalStatus.REJECTED)
                .build();

        mockProposal5 = GameProposal.builder()
                .id(5L)
                .user(mockUser3)
                .gameTitle("Test Game4")
                .status(ProposalStatus.WAITING)
                .build();

        mockChangeProposal = GameProposal.builder()
                .id(1L)
                .user(mockUser1)
                .gameTitle("Test Game1")
                .status(ProposalStatus.REJECTED)
                .build();
    }

    // ✅성공 케이스
    @Test
    void 요청_생성_성공() {
        // Given
        CreateGameProposalRequest request = new CreateGameProposalRequest("Test Game", "This is a test game.");
        when(userConnector.findById(1L)).thenReturn(mockUser1);
        when(gameProposalConnector.existsByGameTitle("Test Game")).thenReturn(false);
        when(gameProposalConnector.save(any(GameProposal.class))).thenReturn(mockProposal1);

        // When
        GameProposalResponse response = gameProposalService.createProposal(request, 1L);

        // Then
        assertNotNull(response);
        assertEquals("Test Game1", response.gameTitle());
        assertEquals(ProposalStatus.WAITING, response.status());
    }

    // ✅성공 케이스
    @Test
    void 모든_리뷰_조회() {
        // GIVEN
        when(gameProposalConnector.findAll())
                .thenReturn(List.of(
                        mockProposal1, mockProposal2, mockProposal3, mockProposal4, mockProposal5
                ));

        // WHEN
        GameProposalGetManyResponse response = gameProposalService.getAllProposals();

        // THEN
        assertEquals(5, response.proposals().size());
        assertEquals(mockProposal1.getGameTitle(), response.proposals().get(0).gameTitle());
        assertEquals(mockProposal2.getGameTitle(), response.proposals().get(1).gameTitle());
        assertEquals(mockProposal3.getGameTitle(), response.proposals().get(2).gameTitle());
        assertEquals(mockProposal4.getGameTitle(), response.proposals().get(3).gameTitle());
        assertEquals(mockProposal5.getGameTitle(), response.proposals().get(4).gameTitle());
    }

    // ✅성공 케이스
    @Test
    void 상태별_요청_조회() {
        // GIVEN
        when(gameProposalConnector.findAllByStatus(ProposalStatus.WAITING))
                .thenReturn(List.of(mockProposal1, mockProposal3, mockProposal5));

        // WHEN
        GameProposalGetManyResponse response = gameProposalService.getProposalsByStatus(ProposalStatus.WAITING);

        // THEN
        assertEquals(3, response.proposals().size());
        assertEquals(mockProposal1.getGameTitle(), response.proposals().get(0).gameTitle());
        assertEquals(mockProposal3.getGameTitle(), response.proposals().get(1).gameTitle());
        assertEquals(mockProposal5.getGameTitle(), response.proposals().get(2).gameTitle());
    }

    // ✅성공 케이스
    @Test
    void 특정_사용자_요청_보기() {
        // GIVEN
        when(gameProposalConnector.findAllByUserId(1L))
                .thenReturn(List.of(mockProposal1, mockProposal2));

        // WHEN
        GameProposalGetManyResponse response = gameProposalService.getProposalsByUserId(1L);

        // THEN
        assertEquals(2, response.proposals().size());
        assertEquals(mockProposal1.getUser().getLoginId(), response.proposals().get(0).userLoginId());
        assertEquals(mockProposal2.getUser().getLoginId(), response.proposals().get(1).userLoginId());
    }

    // ✅성공 케이스
    @Test
    void 내_요청_취소하기() {
        // GIVEN
        Long userId = 1L;
        Long proposalId = 1L;
        when(gameProposalConnector.findById(proposalId)).thenReturn(mockProposal1);
        doReturn(1L).when(mockUser1).getId();

        // WHEN
        GameProposalResponse response = gameProposalService.cancelProposal(proposalId, userId);

        // THEN
        assertEquals(ProposalStatus.CANCELED, response.status());
    }

    // ✅성공 케이스
    @Test
    void 관리자_리뷰_승인() {
        // Given
        Long proposalId = 1L;
        Long adminId = 1L;
        ReviewGameProposalRequest request = new ReviewGameProposalRequest(ProposalStatus.APPROVED);

        when(gameProposalConnector.findById(proposalId)).thenReturn(mockProposal1);
        when(adminConnector.findById(adminId)).thenReturn(mockAdmin);

        // When
        GameProposalResponse response = gameProposalService.reviewProposal(proposalId, request, adminId);

        // Then
        assertNotNull(response);
        assertEquals(ProposalStatus.APPROVED, response.status());

        verify(gameService, times(1)).createGame(any());
    }

    // ✅성공 케이스
    @Test
    void 관리자_리뷰_거절() {
        // Given
        Long proposalId = 1L;
        Long adminId = 1L;
        ReviewGameProposalRequest request = new ReviewGameProposalRequest(ProposalStatus.REJECTED);

        when(gameProposalConnector.findById(proposalId)).thenReturn(mockProposal1);
        when(adminConnector.findById(adminId)).thenReturn(mockAdmin);

        // When
        GameProposalResponse response = gameProposalService.reviewProposal(proposalId, request, adminId);

        // Then
        assertNotNull(response);
        assertEquals(ProposalStatus.REJECTED, response.status());
    }

    // ❌실패 케이스
    @Test
    void 이미_존재하는_게임요청이라_바로_거절() {
        // Given
        CreateGameProposalRequest request = new CreateGameProposalRequest("Test Game", "This is a test game.");
        when(userConnector.findById(1L)).thenReturn(mockUser1);
        when(gameProposalConnector.existsByGameTitle("Test Game")).thenReturn(true);
        when(gameProposalConnector.save(any(GameProposal.class))).thenReturn(mockChangeProposal);

        // When
        GameProposalResponse response = gameProposalService.createProposal(request, 1L);

        // Then
        assertNotNull(response);
        assertEquals("Test Game1", response.gameTitle());
        assertEquals(ProposalStatus.REJECTED, response.status());
    }

    // ❌실패 케이스
    @Test
    void 요청_취소_시도_근데이제_내_요청이_아닌() {
        // GIVEN
        Long userId = 2L;
        Long proposalId = 1L;
        when(gameProposalConnector.findById(proposalId)).thenReturn(mockProposal1);
        doReturn(1L).when(mockUser1).getId();

        // WHEN & THEN
        assertThrows(AccessDeniedException.class, () ->
                gameProposalService.cancelProposal(proposalId, userId));
    }

    // ❌실패 케이스
    @Test
    void 이미_리뷰했는데_취소_시도() {
        // GIVEN
        Long proposalId = 3L;
        when(gameProposalConnector.findById(proposalId)).thenReturn(mockProposal3);

        // WHEN & THEN
        assertThrows(ConflictException.class, () ->
                gameProposalService.cancelProposal(proposalId, 2L));
    }

    // ❌실패 케이스
    @Test
    void 존재하지_않는_관리자로_리뷰시_예외() {
        // Given
        Long proposalId = 1L;
        Long adminId = 999L; // 존재하지 않는 관리자
        ReviewGameProposalRequest request = new ReviewGameProposalRequest(ProposalStatus.APPROVED);

        when(gameProposalConnector.findById(proposalId)).thenReturn(mockProposal1);
        when(adminConnector.findById(adminId)).thenThrow(new NotFoundException(ErrorCode.NOT_FOUND_ID));

        // When & Then
        assertThrows(NotFoundException.class, () ->
                gameProposalService.reviewProposal(proposalId, request, adminId));

        verify(gameProposalConnector, never()).save(any());
    }
}