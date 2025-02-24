package com.example.codePicasso.domain.chat.service;

import com.example.codePicasso.domain.chat.dto.request.RoomRequest;
import com.example.codePicasso.domain.chat.dto.request.UpdateRoomRequest;
import com.example.codePicasso.domain.chat.dto.response.RoomListResponse;
import com.example.codePicasso.domain.chat.dto.response.RoomResponse;
import com.example.codePicasso.domain.chat.entity.ChatRoom;
import com.example.codePicasso.domain.user.entity.User;
import com.example.codePicasso.domain.user.service.UserConnector;
import com.example.codePicasso.global.common.DtoFactory;
import com.example.codePicasso.global.config.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomConnector roomConnector;
    private final UserConnector userConnector;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    public RoomResponse addRoom(RoomRequest roomRequest, Long userId) {
        User user = userConnector.findById(userId);
        String encodedPassword = extracted(roomRequest);

        ChatRoom chatRoom = roomRequest.toEntity(user,encodedPassword);
        ChatRoom saveChatRoom = roomConnector.save(chatRoom);
        return DtoFactory.toChatRoomDto(saveChatRoom);
    }

    @Transactional(readOnly = true)
    public RoomListResponse getAllRoom() {
        List<ChatRoom> chatRooms = roomConnector.findAll();
        List<RoomResponse> roomResponses = chatRooms.stream().map(DtoFactory::toChatRoomDto).toList();
        return RoomListResponse.builder()
                .roomResponses(roomResponses)
                .build();
    }

    @Transactional(readOnly = true)
    public RoomResponse getByRoomName(String roomName) {
        ChatRoom chatRoom = roomConnector.findByName(roomName);
        return DtoFactory.toChatRoomDto(chatRoom);
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
        if (!passwordEncoder.matches(updateRoomRequest.password(), chatRoom.getPassword())) {
            String encodedPassword = passwordEncoder.encode(updateRoomRequest.password());
            chatRoom.updatePassword(encodedPassword);
        }
        if (chatRoom.isSecurity()!= updateRoomRequest.isSecurity()) {
            chatRoom.updateSecurity(updateRoomRequest.isSecurity());
        }

        return DtoFactory.toChatRoomDto(chatRoom);
    }

    @Transactional
    public void deleteRoom(Long roomId, Long userId) {
        ChatRoom chatRoom = roomConnector.findByIdAndUserId(roomId, userId);
        roomConnector.deleteRoom(chatRoom);
    }

    private String extracted(RoomRequest roomRequest) {
        if (!roomRequest.password().isEmpty()) {
            return passwordEncoder.encode(roomRequest.password());
        }
        return null;
    }
}
