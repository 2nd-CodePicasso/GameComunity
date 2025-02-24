package com.example.codePicasso.domain.chat.controller;

import com.example.codePicasso.domain.chat.dto.request.RoomRequest;
import com.example.codePicasso.domain.chat.dto.request.UpdateRoomRequest;
import com.example.codePicasso.domain.chat.dto.response.RoomListResponse;
import com.example.codePicasso.domain.chat.dto.response.RoomResponse;
import com.example.codePicasso.domain.chat.service.RoomService;
import com.example.codePicasso.global.common.ApiResponse;
import com.example.codePicasso.global.common.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class RoomController {


    // v2임
    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<ApiResponse<RoomResponse>> addRoom(
            @RequestBody RoomRequest roomRequest,
            @AuthenticationPrincipal CustomUser user
            ) {
        RoomResponse room = roomService.addRoom(roomRequest, user.getUserId());
        return ApiResponse.created(room);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<RoomListResponse>> getAllRoom() {
        RoomListResponse allRoom = roomService.getAllRoom();
        return ApiResponse.success(allRoom);
    }

    @GetMapping("/{roomName}")
    public ResponseEntity<ApiResponse<RoomResponse>> getByRoomName(
            @PathVariable String roomName
    ) {
        RoomResponse room = roomService.getByRoomName(roomName);
        return ApiResponse.success(room);
    }

    @PatchMapping
    public ResponseEntity<ApiResponse<RoomResponse>> updateRoom(
            @RequestAttribute Long userId,
            @RequestBody UpdateRoomRequest updateRoomRequest
    ) {

        RoomResponse roomResponse = roomService.updateRoom(updateRoomRequest, userId);
        return ApiResponse.success(roomResponse);
    }
}
