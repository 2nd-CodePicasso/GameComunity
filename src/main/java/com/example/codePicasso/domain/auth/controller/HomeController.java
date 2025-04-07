package com.example.codePicasso.domain.auth.controller;

import com.example.codePicasso.domain.auth.service.KaKaoService;
import com.example.codePicasso.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final KaKaoService kaKaoService;

    @GetMapping("/auth/hi/kakao/signup")
    public String kakaoCallback(@RequestParam("code") String code, Model model) {
        String token = kaKaoService.requestKakaoToken(code);
        model.addAttribute("kakaoToken", token);

        return "login_callback";
    }

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
    @GetMapping("/boardmain/hi/game")
    public String boardMain() {
        return "board_main";
    }
    @GetMapping("/boardeatail/hi/game")
    public String boarDetailMain() {
        return "board_details";
    }
    @GetMapping("/proposal/hi/game")
    public String proposal() {
        return "proposal_admin";
    }
    @GetMapping("/main/hi/admin")
    public String admin() {
        return "main_admin";
    }


}
