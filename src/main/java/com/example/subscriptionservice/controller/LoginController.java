// LoginController.java
// 경로: com.example.subscriptionservice.controller.LoginController

package com.example.subscriptionservice.controller;

import com.example.subscriptionservice.repository.UserRepository;
import com.example.subscriptionservice.domain.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller // 로그인 관련 요청을 처리하는 컨트롤러
@RequiredArgsConstructor // 생성자 주입
public class LoginController {

    private final UserRepository userRepository;

    // 로그인 폼 보여주기
    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // templates/login.html
    }

    // 로그인 요청 처리
    @PostMapping("/login")
    public String handleLogin(@RequestParam String username,
                              @RequestParam String password,
                              HttpSession session,
                              Model model) {

        // 사용자 이름으로 조회
        User user = userRepository.findByUsername(username).orElse(null);

        // 사용자 존재 여부 및 비밀번호 확인
        if (user == null || !user.getPassword().equals(password)) {
            model.addAttribute("error", "아이디 또는 비밀번호가 올바르지 않습니다.");
            return "login";
        }

        // 로그인 성공 시 세션에 사용자 정보 저장
        session.setAttribute("loggedInUser", user);
        return "redirect:/home"; // 로그인 후 홈으로 이동
    }

    // 로그아웃 처리
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 초기화
        return "redirect:/login";
    }
}