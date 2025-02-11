package com.example.codePicasso.domain.communities.controller;

import com.example.codePicasso.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/communities")
public class CommunityController {

    @GetMapping
    public ResponseEntity<ApiResponse<Void>> findAll() {
        return ApiResponse.noContent();
    }
}
