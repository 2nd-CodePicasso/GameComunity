package com.example.codePicasso.domain.auth.controller;

import com.example.codePicasso.domain.auth.dto.request.SigninRequest;
import com.example.codePicasso.domain.auth.dto.response.JwtTokenResponse;
import com.example.codePicasso.domain.auth.service.AuthService;
import com.example.codePicasso.domain.users.dto.response.AdminResponse;
import com.example.codePicasso.domain.users.dto.request.AdminRequest;
import com.example.codePicasso.domain.users.dto.request.UserRequest;
import com.example.codePicasso.domain.users.dto.response.UserResponse;
import com.example.codePicasso.domain.users.service.AdminService;
import com.example.codePicasso.domain.users.service.UserService;
import com.example.codePicasso.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final AdminService adminService;
    private final AuthService authService;

    @PostMapping("/users")
    public ResponseEntity<ApiResponse<UserResponse>> addUser(@RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.addUser(userRequest);
        return ApiResponse.created(userResponse);
    }

    @PostMapping("/admin")
    public ResponseEntity<ApiResponse<AdminResponse>> addAdmin(@RequestBody AdminRequest adminRequest) {
        AdminResponse adminResponse = adminService.addAdmin(adminRequest);
        return ApiResponse.created(adminResponse);
    }

    @PostMapping("/users/login")
    public ResponseEntity<ApiResponse<JwtTokenResponse>> loginUser(@RequestBody SigninRequest signinRequest) {
        JwtTokenResponse jwtTokenResponse = authService.loginUser(signinRequest);
        return ApiResponse.success(jwtTokenResponse);
    }

    @PostMapping("/admin/login")
    public ResponseEntity<ApiResponse<JwtTokenResponse>> loginAdmin(@RequestBody SigninRequest signinRequest) {
        JwtTokenResponse jwtTokenResponse = authService.loginAdmin(signinRequest);
        return ApiResponse.success(jwtTokenResponse);
    }
}
