package com.example.subscriptionservice.controller;

import com.example.subscriptionservice.Users;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/hello")
    public String hello(HttpSession session, Model model) {
        Users loginUser = (Users) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";  // 로그인 안 된 경우 로그인 페이지로 이동
        }
        model.addAttribute("name", loginUser.getName());
        return "hello";
    }

}
