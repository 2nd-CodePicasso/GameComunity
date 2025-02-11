package com.example.codePicasso.domain.auth.service;

import com.example.codePicasso.domain.auth.dto.request.SigninRequest;
import com.example.codePicasso.domain.auth.dto.response.JwtTokenResponse;
import com.example.codePicasso.domain.users.entity.Admin;
import com.example.codePicasso.domain.users.entity.User;
import com.example.codePicasso.domain.users.service.AdminConnector;
import com.example.codePicasso.domain.users.service.UserConnector;
import com.example.codePicasso.global.config.JwtUtil;
import com.example.codePicasso.global.config.PasswordEncoder;
import com.example.codePicasso.global.exception.base.InvalidRequestException;
import com.example.codePicasso.global.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserConnector userConnector;
    private final AdminConnector adminConnector;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public JwtTokenResponse loginUser(SigninRequest signinRequest) {
        User user = userConnector.findByLoginId(signinRequest.loginId());

        if (!passwordEncoder.matches(signinRequest.password(), user.getPassword())) {
            throw new InvalidRequestException(ErrorCode.PW_ERROR);
        }
        String token = jwtUtil.createToken(user.getId());

        return JwtTokenResponse.builder().token(token).build();
    }

    public JwtTokenResponse loginAdmin(SigninRequest signinRequest) {
        Admin admin = adminConnector.findByLoginId(signinRequest.loginId());

        if (!passwordEncoder.matches(signinRequest.password(), admin.getPassword())) {
            throw new InvalidRequestException(ErrorCode.PW_ERROR);
        }
        String token = jwtUtil.createToken(admin.getId());
        return JwtTokenResponse.builder().token(token).build();
    }
}
