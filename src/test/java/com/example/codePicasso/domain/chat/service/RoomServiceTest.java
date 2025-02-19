package com.example.codePicasso.domain.chat.service;

import com.example.codePicasso.domain.category.entity.Category;
import com.example.codePicasso.domain.chat.dto.request.ChatRequest;
import com.example.codePicasso.domain.chat.dto.request.RoomRequest;
import com.example.codePicasso.domain.chat.dto.request.UpdateRoomRequest;
import com.example.codePicasso.domain.chat.dto.response.RoomResponse;
import com.example.codePicasso.domain.chat.entity.Chat;
import com.example.codePicasso.domain.chat.entity.ChatRoom;
import com.example.codePicasso.domain.chat.entity.GlobalChat;
import com.example.codePicasso.domain.game.entity.Game;
import com.example.codePicasso.domain.post.entity.Post;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

    @InjectMocks
    private RoomService roomService;

    @Mock
    private UserConnector userConnector;

    @Mock
    private RoomConnector roomConnector;

    private UpdateRoomRequest updateRoomRequest;
    private Long userId = 1L;
    private Long roomId = 1L;
    private User user;
    private ChatRoom chatRoom;
    private RoomRequest roomRequest;
    private List<ChatRoom> chatRooms = new ArrayList<>();

    @BeforeEach
    void 설정() {
        user = new User("user", "testUser", "user123");  // 예시: User 객체 생성
        roomRequest = new RoomRequest("집에가고싶은방");
        updateRoomRequest = new UpdateRoomRequest(roomId, "쀅", "박씨");
        chatRoom = roomRequest.toEntity(user);

        chatRooms.add(chatRoom);
    }

    @Test
    void 룸_생성() {
        //given

        //when
        when(userConnector.findById(userId)).thenReturn(user);
        when(roomConnector.save(any(ChatRoom.class))).thenReturn(chatRoom);
        RoomResponse roomResponse = roomService.addRoom(roomRequest, userId);

        //then
        verify(userConnector).findById(userId);
        verify(roomConnector).save(any(ChatRoom.class));
        assertEquals(chatRoom.getName(), roomResponse.roomName());
    }

    @Test
    void 룸_모두조회() {
        //given
        //when
        when(roomConnector.findAll()).thenReturn(chatRooms);
        List<RoomResponse> roomResponses = roomService.getAllRoom();

        //then
        verify(roomConnector).findAll();
        assertEquals(chatRooms.get(0).getName(), roomResponses.get(0).roomName());
    }

    @Test
    void 룸_이름으로조회() {
        //given
        String roomName = "집에가고싶은방";
        //when
        when(roomConnector.findByName(roomName)).thenReturn(chatRoom);
        RoomResponse roomResponse = roomService.getByRoomName(roomName);

        //then
        verify(roomConnector).findByName(roomName);
        assertEquals(roomName, roomResponse.roomName());
    }

    @Test
    void 룸_정보수정() {
        //given
        //when
        when(roomConnector.findByIdAndUserId(updateRoomRequest.roomId(), userId)).thenReturn(chatRoom);
        when(userConnector.findByNickname(updateRoomRequest.name())).thenReturn(user);
        RoomResponse roomResponse = roomService.updateRoom(updateRoomRequest, userId);

        //then
        verify(roomConnector).findByIdAndUserId(updateRoomRequest.roomId(), userId);
        verify(userConnector).findByNickname(updateRoomRequest.name());
        assertEquals(chatRoom.getName(), roomResponse.roomName());
        assertEquals(chatRoom.getUser().getNickname(), roomResponse.username());
    }

    @Test
    void 룸_삭제() {
        //given
        //when
        when(roomConnector.findByIdAndUserId(roomId, userId)).thenReturn(chatRoom);
        roomService.deleteRoom(roomId, userId);
        //then
        verify(roomConnector).findByIdAndUserId(roomId, userId);
    }
}
