package com.example.codePicasso.domain.comment.service;

import com.example.codePicasso.domain.category.entity.Category;
import com.example.codePicasso.domain.comment.dto.request.CommentRequest;
import com.example.codePicasso.domain.comment.dto.response.CommentListResponse;
import com.example.codePicasso.domain.comment.dto.response.CommentResponse;
import com.example.codePicasso.domain.comment.entity.Comment;
import com.example.codePicasso.domain.game.entity.Game;
import com.example.codePicasso.domain.post.entity.Post;
import com.example.codePicasso.domain.post.service.PostConnector;
import com.example.codePicasso.domain.user.entity.Admin;
import com.example.codePicasso.domain.user.entity.User;
import com.example.codePicasso.domain.user.service.UserConnector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static java.awt.SystemColor.text;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentConnector commentConnector;

    @Mock
    private PostConnector postConnector;

    @Mock
    private UserConnector userConnector;

    private User mockUser;
    private Admin mockAdmin;
    private Game mockGame;
    private Category mockCategory;
    private Post mockPost;
    private Comment mockComment;
    private Comment mockReply;
    private List<Comment> comments = new ArrayList<>();
    private List<Comment> replies = new ArrayList<>();


    @BeforeEach
    void setUp() {
        mockUser = new User("user", "testUser", "password");
        mockAdmin = new Admin("admin", "password");
        mockGame = Game.builder()
                .id(1L)
                .admin(mockAdmin)
                .gameTitle("test Game Title")
                .gameDescription("test Game")
                .build();
        mockCategory = Category.builder()
                .id(1L)
                .game(mockGame)
                .categoryName("test Category")
                .build();
        mockPost = Post.builder()
                .id(1L)
                .user(mockUser)
                .game(mockGame)
                .category(mockCategory)
                .title("test Title")
                .description("test Post")
                .build();
        mockComment = Comment.builder()
                .post(mockPost)
                .user(mockUser)
                .text("test Comment")
                .build();
        mockReply = Comment.builder()
                .post(mockPost)
                .user(mockUser)
                .parent(mockComment)
                .text("test Reply")
                .build();
        comments.add(mockComment);
        replies.add(mockReply);

    }

    @Test
        // 댓글 생성
    void createComment() {
        // Given
        Long postId = 1L;
        Long userId = 1L;
        CommentRequest request = new CommentRequest("test Comment");

        // When
        when(postConnector.findById(postId)).thenReturn(mockPost);
        when(userConnector.findById(userId)).thenReturn(mockUser);
        when(commentConnector.save(any(Comment.class))).thenReturn(mockComment);

        CommentResponse response = commentService.createComment(postId, userId, request);

        // Then
        verify(commentConnector, times(1)).save(any());
        verify(postConnector).findById(postId);
        verify(userConnector).findById(userId);

        assertEquals(mockPost.getId(), response.postId());
        assertEquals(mockUser.getId(), response.userId());
        assertEquals(request.text(), response.text());
        assertThat(request.text()).isEqualTo(response.text());
    }

    @Test
        // 대댓글 생성
    void createReply() {
        Long postId = 1L;
        Long userId = 1L;
        Long parentId = 1L;
        CommentRequest request = new CommentRequest("test Reply");

        // When
        when(postConnector.findById(postId)).thenReturn(mockPost);
        when(userConnector.findById(userId)).thenReturn(mockUser);
        when(commentConnector.findById(parentId)).thenReturn(mockComment);
        when(commentConnector.save(any(Comment.class))).thenReturn(mockReply);

        CommentResponse response = commentService.createReply(postId, parentId, userId, request);

        // Then
        verify(commentConnector, times(1)).save(any());
        verify(postConnector).findById(postId);
        verify(userConnector).findById(userId);
        verify(commentConnector).findById(parentId);

        assertEquals(mockPost.getId(), response.postId());
        assertEquals(mockUser.getId(), response.userId());
        assertEquals(mockComment.getId(), response.parentId());
        assertEquals(request.text(), response.text());
    }

    @Test
        // 전체 댓글 조회 (대댓글 포함)
    void findAllByPostId() {
        // Given
        Long postId = 1L;

        mockComment.getReplies().clear();
        mockComment.addReplies(mockReply);

        comments.clear();
        comments.add(mockComment);

        // When
        when(commentConnector.findAllByPostId(postId)).thenReturn(comments);
        CommentListResponse commentListResponse = commentService.findAllByPostId(postId);

        // Then
        verify(commentConnector).findAllByPostId(postId);

        List<CommentResponse> responseList = commentListResponse.commentResponses();
        assertFalse(responseList.isEmpty(), "Not found comment List");

        CommentResponse getComment = responseList.get(0);
        assertEquals(mockComment.getId(), getComment.commentId());
        assertEquals(mockComment.getPost().getId(), getComment.postId());
        assertEquals(mockComment.getUser().getId(), getComment.userId());
        assertEquals(mockComment.getUser().getNickname(), getComment.nickname());
        assertEquals(mockComment.getText(), getComment.text());
        assertNull(getComment.parentId());

        List<CommentResponse> replies = getComment.replies();
        assertFalse(replies.isEmpty(), "The replies list is empty");

        CommentResponse getReply = replies.get(0);
        assertEquals(mockReply.getId(), getReply.commentId());
        assertEquals(mockReply.getPost().getId(), getReply.postId());
        assertEquals(mockReply.getUser().getId(), getReply.userId());
        assertEquals(mockReply.getUser().getNickname(), getReply.nickname());
        assertEquals(mockReply.getText(), getReply.text());
        assertEquals(mockComment.getId(), getReply.parentId());
    }

    @Test
        // 댓글 수정
    void updateComment() {
        // Given
        Long commentId = 1L;
        Long userId = 1L;
        CommentRequest request = new CommentRequest("update Comment");

        // When
        when(commentConnector.findByIdAndUserId(commentId, userId)).thenReturn(mockComment);

        CommentResponse commentResponse = commentService.updateComment(commentId, userId, request);

        // Then
        verify(commentConnector).findByIdAndUserId(commentId, userId);

        assertEquals(request.text(), commentResponse.text());
    }

    @Test
    void deleteComment() {
        //Given
        Long commentId = 1L;
        Long userId = 1L;

        // When
        when(commentConnector.findByIdAndUserId(commentId, userId)).thenReturn(mockComment);
        commentService.deleteComment(commentId, userId);

        // Then

        verify(commentConnector).findByIdAndUserId(commentId, userId);
        verify(commentConnector).delete(mockComment);

    }
}