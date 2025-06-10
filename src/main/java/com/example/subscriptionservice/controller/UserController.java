// UserController.java
// 경로: com.example.subscriptionservice.controller.UserController

package com.example.subscriptionservice.controller;

import com.example.subscriptionservice.domain.User;
import com.example.subscriptionservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller // 이 클래스가 Spring MVC의 컨트롤러임을 나타냄
@RequiredArgsConstructor // 생성자 주입을 위한 Lombok 애너테이션
@RequestMapping("/user") // 이 컨트롤러의 기본 URL 경로 접두어 설정
public class UserController {

    private final UserRepository userRepository; // 의존성 주입: 회원 저장소

    // 회원가입 폼을 보여주는 메서드
    @GetMapping("/signup")
    public String showSignupForm() {
        return "signup"; // templates/signup.html 렌더링
    }

    // 회원가입 요청을 처리하는 메서드
    @PostMapping("/signup")
    public String handleSignup(@RequestParam String username, // 폼에서 전송된 사용자 아이디
                               @RequestParam String password, // 폼에서 전송된 비밀번호
                               Model model) {

        // 아이디 중복 여부 확인
        if (userRepository.findByUsername(username).isPresent()) {
            model.addAttribute("error", "이미 존재하는 사용자명입니다.");
            return "signup"; // 에러 메시지와 함께 회원가입 폼 다시 보여줌
        }

        // 새 사용자 저장 (비밀번호 암호화 없이 저장. 실전에서는 암호화 필요)
        userRepository.save(User.builder()
                .username(username)
                .password(password)
                .build());

        return "redirect:/login"; // 회원가입 성공 후 로그인 페이지로 이동
    }
}
