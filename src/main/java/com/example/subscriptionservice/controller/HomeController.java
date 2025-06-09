package com.example.subscriptionservice.controller;

import com.example.subscriptionservice.Users;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/", "/home"})
    public String home(HttpSession session, Model model) {
        Users user = (Users) session.getAttribute("loginUser");
        if (user == null) {
            return "redirect:/login";  // 로그인 안 되어있으면 로그인 페이지로
        }
        model.addAttribute("user", user);
        return "home";  // src/main/resources/templates/home.html
    }
}
