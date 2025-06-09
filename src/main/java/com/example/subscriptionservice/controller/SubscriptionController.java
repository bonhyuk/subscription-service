package com.example.subscriptionservice.controller;

import com.example.subscriptionservice.Users;
import com.example.subscriptionservice.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SubscriptionController {

    private final UserRepository userRepository;

    public SubscriptionController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 구독 취소 요청 처리
    @PostMapping("/unsubscribe")
    public String unsubscribe(HttpSession session) {
        Users user = (Users) session.getAttribute("loginUser");
        if (user != null) {
            user.setSubscribed(false);  // 구독 상태 false로 변경
            userRepository.save(user);
        }
        return "redirect:/home";  // 취소 후 홈 화면으로 리다이렉트
    }

    // 구독 신청 폼 (선택)
    @GetMapping("/subscribe")
    public String showSubscribeForm() {
        return "subscribe";  // subscribe.html
    }

    @PostMapping("/subscribe")
    public String subscribe(HttpSession session) {
        Users user = (Users) session.getAttribute("loginUser");
        if (user != null) {
            user.setSubscribed(true);  // 구독 상태 true로 변경
            userRepository.save(user);
        }
        return "redirect:/home";
    }

}
