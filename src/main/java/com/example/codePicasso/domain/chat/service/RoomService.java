package com.example.codePicasso.domain.chat.service;

import com.example.codePicasso.domain.chat.dto.request.UpdateRoomRequest;
import com.example.codePicasso.domain.chat.dto.response.RoomResponse;
import com.example.codePicasso.domain.chat.dto.request.RoomRequest;
import com.example.codePicasso.domain.chat.entity.ChatRoom;
import com.example.codePicasso.domain.user.entity.User;
import com.example.codePicasso.domain.user.service.UserConnector;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomConnector roomConnector;
    private final UserConnector userConnector;

    @Transactional
    public RoomResponse addRoom(RoomRequest roomRequest, Long userId) {
        User user = userConnector.findById(userId);
        ChatRoom chatRoom = roomRequest.toEntity(user);
        roomConnector.save(chatRoom);
        return chatRoom.toDto();
    }

    @Transactional(readOnly = true)
    public List<RoomResponse> getAllRoom() {
        List<ChatRoom> chatRooms = roomConnector.findAll();
        return chatRooms.stream().map(ChatRoom::toDto).toList();
    }

    @Transactional(readOnly = true)
    public RoomResponse getByRoomName(String roomName) {
        ChatRoom chatRoom = roomConnector.findByName(roomName);
        return chatRoom.toDto();
    }

    @Transactional
    public RoomResponse updateRoom(UpdateRoomRequest updateRoomRequest, Long userId) {
        ChatRoom chatRoom = roomConnector.findByIdAndUserId(updateRoomRequest.roomId(), userId);
        if (!chatRoom.getName().equals(updateRoomRequest.name())) {
            chatRoom.updateName(updateRoomRequest.name());
        }
        if (!chatRoom.getUser().getNickname().equals(updateRoomRequest.username())) {
            User user = userConnector.findByNickname(updateRoomRequest.name());
            chatRoom.updateUser(user);
        }

        return chatRoom.toDto();
    }

    public void deleteRoom(Long roomId, Long userId) {
        ChatRoom chatRoom = roomConnector.findByIdAndUserId(roomId, userId);
        roomConnector.deleteRoom(chatRoom);
    }
}
