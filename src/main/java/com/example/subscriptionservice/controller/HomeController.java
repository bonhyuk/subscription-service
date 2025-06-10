package com.example.subscriptionservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // /home URL 요청을 받아서 home.html 뷰를 반환
    @GetMapping("/home")
    public String home() {
        return "home"; // templates/home.html 파일 필요
    }
}
