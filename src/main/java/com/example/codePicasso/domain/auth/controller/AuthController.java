package com.example.codePicasso.domain.auth.controller;

import com.example.codePicasso.domain.auth.dto.request.KakaoRequest;
import com.example.codePicasso.domain.auth.dto.request.SignInRequest;
import com.example.codePicasso.domain.auth.dto.response.JwtTokenResponse;
import com.example.codePicasso.domain.auth.service.AuthService;
import com.example.codePicasso.domain.auth.service.KaKaoService;
import com.example.codePicasso.domain.user.dto.request.AdminRequest;
import com.example.codePicasso.domain.user.dto.request.UserRequest;
import com.example.codePicasso.domain.user.dto.response.AdminResponse;
import com.example.codePicasso.domain.user.dto.response.UserResponse;
import com.example.codePicasso.domain.user.service.AdminService;
import com.example.codePicasso.domain.user.service.UserService;
import com.example.codePicasso.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/hi")
public class AuthController {
    private final UserService userService;
    private final AdminService adminService;
    private final AuthService authService;
    private final KaKaoService kaKaoService;

    @PostMapping("/kakao/login")
    public ResponseEntity<ApiResponse<JwtTokenResponse>> loginKakao(
            @RequestBody KakaoRequest kakaoRequest
    ) {
        Long kakaoId = kaKaoService.getKakaoId(kakaoRequest.kakaoToken());
        JwtTokenResponse jwtTokenResponse = authService.loginKakao(kakaoId);
        return ApiResponse.success(jwtTokenResponse);
    }

    @PostMapping("/users")
    public ResponseEntity<ApiResponse<UserResponse>> addUser(
            @RequestBody UserRequest userRequest
    ) {
        UserResponse userResponse = userService.addUser(userRequest);

        return ApiResponse.created(userResponse);
    }

    @PostMapping("/admin")
    public ResponseEntity<ApiResponse<AdminResponse>> addAdmin(
            @RequestBody AdminRequest adminRequest
    ) {
        AdminResponse adminResponse = adminService.addAdmin(adminRequest);

        return ApiResponse.created(adminResponse);
    }

    @PostMapping("/users/login")
    public ResponseEntity<ApiResponse<JwtTokenResponse>> loginUser(
            @RequestBody SignInRequest signinRequest
    ) {
        JwtTokenResponse jwtTokenResponse = authService.loginUser(signinRequest);

        return ApiResponse.success(jwtTokenResponse);
    }

    @PostMapping("/admin/login")
    public ResponseEntity<ApiResponse<JwtTokenResponse>> loginAdmin(
            @RequestBody SignInRequest signinRequest
    ) {
        JwtTokenResponse jwtTokenResponse = authService.loginAdmin(signinRequest);

        return ApiResponse.success(jwtTokenResponse);
    }
}
