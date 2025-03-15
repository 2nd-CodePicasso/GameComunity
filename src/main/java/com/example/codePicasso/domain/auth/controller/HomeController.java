package com.example.codePicasso.domain.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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

    @GetMapping("/main/hi/game")
    public String main() {
        return "main";
    }

    @GetMapping("/signup/hi/game")
    public String signup() {
        return "signup";
    }

    @GetMapping("/exchange/hi/game")
    public String exchange() {
        return "exchange";
    }

    @GetMapping("/gameboard/hi/game")
    public String gameBoard() {
        return "gameboard";
    }
}
