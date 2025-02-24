package com.example.codePicasso.domain.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @GetMapping("/login/hi/users")
    public String home() {
        return "index";
    }

    @GetMapping("/chat/hi/lol")
    public String chat() {
        return "chat";
    }
}
