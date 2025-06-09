package com.example.subscriptionservice.controller;

import com.example.subscriptionservice.UserRepository;
import com.example.subscriptionservice.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/join")
    public String joinForm() {
        return "join";
    }

    @PostMapping("/join")
    public String join(@RequestParam String name,
                       @RequestParam String email,
                       @RequestParam String password,
                       Model model) {
        if (userRepository.findByEmail(email) != null) {
            model.addAttribute("error", "이미 가입된 이메일입니다.");
            return "join";
        }

        Users user = new Users();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        userRepository.save(user);

        return "redirect:/hello";
    }
}
