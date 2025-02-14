package com.example.codePicasso.domain.users.controller;

import com.example.codePicasso.domain.users.dto.request.UserRequest;
import com.example.codePicasso.domain.users.dto.response.UserResponse;
import com.example.codePicasso.domain.users.service.UserService;
import com.example.codePicasso.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
}
