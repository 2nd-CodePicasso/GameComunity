package com.example.codePicasso.domain.chat.service;

import com.example.codePicasso.domain.chat.dto.request.ChatRequest;
import com.example.codePicasso.domain.chat.dto.request.RoomRequest;
import com.example.codePicasso.domain.chat.dto.response.ChatListResponse;
import com.example.codePicasso.domain.chat.dto.response.ChatResponse;
import com.example.codePicasso.domain.chat.dto.response.GlobalChatListResponse;
import com.example.codePicasso.domain.chat.dto.response.GlobalChatResponse;
import com.example.codePicasso.domain.chat.entity.Chat;
import com.example.codePicasso.domain.chat.entity.ChatRoom;
import com.example.codePicasso.domain.chat.entity.GlobalChat;
import com.example.codePicasso.domain.user.entity.User;
import com.vdurmont.emoji.EmojiParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChatServiceTest {

    @InjectMocks
    private ChatService chatService;

    @Mock
    private ChatConnector chatConnector;

    @Mock
    private GlobalChatConnector globalChatConnector;

    @Mock
    private RoomConnector roomConnector;

    private ChatRequest chatRequest;
    private Long userId = 1L;
    private Long roomId = 1L;
    String username = "한씨";
    private User user;
    private GlobalChat globalChat;
    private Chat chat;
    private ChatRoom chatRoom;
    private RoomRequest roomRequest;
    private List<GlobalChat> globalChats = new ArrayList<>();
    private List<Chat> chats = new ArrayList<>();

    @BeforeEach
    void 설정() {
        user = new User("user", "testUser", "user123");  // 예시: User 객체 생성
        roomRequest = new RoomRequest("집에가고싶은방",true,"12345");
        chatRequest = new ChatRequest("집에가고싶다",null);
        chatRoom = roomRequest.toEntity(user, "12345");
        chat = chatRequest.toEntityFromChat(userId, chatRoom, EmojiParser.parseToUnicode(":smile:"),username);
        globalChat = chatRequest.toEntityFromGlobalChat(userId, EmojiParser.parseToUnicode(":smile:"), username);

        globalChats.add(globalChat);
        chats.add(chat);
    }

    @Test
    void 글라발_채팅_생성() {
        //given설정에서 처리함


        //when
        when(globalChatConnector.save(any(GlobalChat.class))).thenReturn(globalChat);
        GlobalChatResponse globalChatResponse = chatService.addForAllRoomToMessage(chatRequest, userId, username);

        //then
        verify(globalChatConnector).save(any(GlobalChat.class));
        assertEquals(globalChat.getContent(), globalChatResponse.message());
        assertEquals(globalChat.getUsername(),globalChatResponse.username());
    }

    @Test
    void 룸_채팅_생성() {
        //given
        //when
        when(roomConnector.findById(roomId)).thenReturn(chatRoom);
        when(chatConnector.save(any(Chat.class))).thenReturn(chat);
        ChatResponse chatResponse = chatService.addForRoomToMessage(chatRequest, roomId, userId,username);

        //then
        verify(roomConnector).findById(roomId);
        verify(chatConnector).save(any(Chat.class));
        assertEquals(chat.getContent(), chatResponse.message());
        assertEquals(chat.getCreatedAt(), chatResponse.createdAt());
        assertEquals(chat.getUsername(), chatResponse.username());
    }
}
