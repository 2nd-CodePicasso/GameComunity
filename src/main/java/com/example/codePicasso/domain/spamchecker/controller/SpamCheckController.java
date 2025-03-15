package com.example.codePicasso.domain.spamchecker.controller;

import com.example.codePicasso.domain.spamchecker.dto.response.ApiResponse;
import com.example.codePicasso.domain.spamchecker.service.SpamCheckService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/spam-check")
@AllArgsConstructor
public class SpamCheckController {
    private final SpamCheckService spamCheckService;

    @GetMapping("/{number}")
    public ApiResponse checkSpam(@PathVariable String number) {
        return spamCheckService.checkSpam(number);
    }
}

