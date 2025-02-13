package com.example.codePicasso.domain.gameProposal.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.codePicasso.domain.gameProposal.dto.request.CreateGameProposalRequest;
import com.example.codePicasso.domain.gameProposal.dto.request.ReviewGameProposalRequest;
import com.example.codePicasso.domain.gameProposal.dto.response.GameProposalResponse;
import com.example.codePicasso.domain.gameProposal.entity.GameProposal;
import com.example.codePicasso.domain.gameProposal.enums.ProposalStatus;
import com.example.codePicasso.domain.games.service.GameService;
import com.example.codePicasso.domain.users.entity.Admin;
import com.example.codePicasso.domain.users.entity.User;
import com.example.codePicasso.domain.users.service.AdminConnector;
import com.example.codePicasso.domain.users.service.UserConnector;
import com.example.codePicasso.global.exception.base.NotFoundException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    private User mockUser;
    private Admin mockAdmin;
    private GameProposal mockProposal;

    @BeforeEach
    void setUp() {
        mockUser = new User("user", "testUser","user123");  // 예시: User 객체 생성
        mockAdmin = new Admin("admin", "testAdmin");
        mockProposal = GameProposal.builder()
                .id(1L)
                .user(mockUser)
                .gameTitle("Test Game")
                .status(ProposalStatus.WAITING)
                .build();
    }

    @Test
    void 요청_생성_성공() {
        // Given
        CreateGameProposalRequest request = new CreateGameProposalRequest("Test Game", "This is a test game.");
        when(userConnector.findById(1L)).thenReturn(mockUser);
        when(gameProposalConnector.existsByGameTitle("Test Game")).thenReturn(false);
        doNothing().when(gameProposalConnector).save(any());

        // When
        GameProposalResponse response = gameProposalService.createProposal(request, 1L);

        // Then
        assertNotNull(response);
        assertEquals("Test Game", response.gameTitle());
        assertEquals(ProposalStatus.WAITING, response.status());

        verify(gameProposalConnector, times(1))
                .save(argThat(proposal -> proposal.getStatus() == ProposalStatus.WAITING));
    }


    @Test
    void 이미_존재하는_게임요청이라_바로_거절() {
        // Given
        CreateGameProposalRequest request = new CreateGameProposalRequest("Test Game", "This is a test game.");
        when(userConnector.findById(1L)).thenReturn(mockUser);
        when(gameProposalConnector.existsByGameTitle("Test Game")).thenReturn(true);
        doNothing().when(gameProposalConnector).save(any());

        // When
        GameProposalResponse response = gameProposalService.createProposal(request, 1L);

        // Then
        assertNotNull(response);
        assertEquals("Test Game", response.gameTitle());
        assertEquals(ProposalStatus.REJECTED, response.status());

        verify(gameProposalConnector, times(1))
                .save(argThat(proposal -> proposal.getStatus() == ProposalStatus.REJECTED));
    }

    @Test
    void 관리자_리뷰_승인() {
        // Given
        Long proposalId = 1L;
        Long adminId = 1L;
        ReviewGameProposalRequest request = new ReviewGameProposalRequest(ProposalStatus.APPROVED);

        when(gameProposalConnector.findById(proposalId)).thenReturn(mockProposal);
        when(adminConnector.findById(adminId)).thenReturn(mockAdmin);
        doNothing().when(gameProposalConnector).save(any());
        doNothing().when(gameService).createGame(any());

        // When
        GameProposalResponse response = gameProposalService.reviewProposal(proposalId, request, adminId);

        // Then
        assertNotNull(response);
        assertEquals(ProposalStatus.APPROVED, response.status());

        verify(gameProposalConnector, times(1)).save(argThat(proposal ->
                proposal.getStatus() == ProposalStatus.APPROVED
        ));
        verify(gameService, times(1)).createGame(any());
    }

    @Test
    void 관리자_리뷰_거절() {
        // Given
        Long proposalId = 1L;
        Long adminId = 1L;
        ReviewGameProposalRequest request = new ReviewGameProposalRequest(ProposalStatus.REJECTED);

        when(gameProposalConnector.findById(proposalId)).thenReturn(mockProposal);
        when(adminConnector.findById(adminId)).thenReturn(mockAdmin);
        doNothing().when(gameProposalConnector).save(any());

        // When
        GameProposalResponse response = gameProposalService.reviewProposal(proposalId, request, adminId);

        // Then
        assertNotNull(response);
        assertEquals(ProposalStatus.REJECTED, response.status());

        verify(gameProposalConnector, times(1)).save(argThat(proposal ->
                proposal.getStatus() == ProposalStatus.REJECTED
        ));
    }

    @Test
    void 존재하지_않는_관리자로_리뷰시_예외() {
        // Given
        Long proposalId = 1L;
        Long adminId = 999L; // 존재하지 않는 관리자
        ReviewGameProposalRequest request = new ReviewGameProposalRequest(ProposalStatus.APPROVED);

        when(gameProposalConnector.findById(proposalId)).thenReturn(mockProposal);
        when(adminConnector.findById(adminId)).thenThrow(new NotFoundException(ErrorCode.NOT_FOUND_ID));

        // When & Then
        assertThrows(NotFoundException.class, () ->
                gameProposalService.reviewProposal(proposalId, request, adminId)
        );

        verify(gameProposalConnector, never()).save(any());
    }
}