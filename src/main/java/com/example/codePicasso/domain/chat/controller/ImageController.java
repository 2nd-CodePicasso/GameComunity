package com.example.codePicasso.domain.chat.controller;

import com.example.codePicasso.domain.chat.dto.response.ImageResponse;
import com.example.codePicasso.domain.chat.service.ImageService;
import com.example.codePicasso.global.common.ApiResponse;
import com.example.codePicasso.global.common.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping
    public ResponseEntity<ApiResponse<ImageResponse>> addImage(
            @RequestParam MultipartFile multipartFile,
            @AuthenticationPrincipal CustomUser user
            ) {
        ImageResponse imageResponse = imageService.addImage(multipartFile,user.getUserId());

        return ApiResponse.created(imageResponse);
    }
}
