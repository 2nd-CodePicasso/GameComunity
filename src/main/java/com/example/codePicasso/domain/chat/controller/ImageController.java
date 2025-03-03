package com.example.codePicasso.domain.chat.controller;

import com.example.codePicasso.domain.chat.service.S3Service;
import com.example.codePicasso.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/images/hi/bye")
@RequiredArgsConstructor
public class ImageController {

    private final S3Service s3Service;

    @GetMapping
    public ResponseEntity<?> addImage(
            @RequestParam String filename
            ) {
        String url = s3Service.makePreSignedUrl(filename);
        return ApiResponse.created(url);
    }
}
