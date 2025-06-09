package com.example.subscriptionservice.interceptor;

import com.example.subscriptionservice.Users;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        HttpSession session = request.getSession(false);
        if (session != null) {
            Users loginUser = (Users) session.getAttribute("loginUser");
            if (loginUser != null) {
                // 로그인 된 사용자면 다음으로 진행
                return true;
            }
        }

        // 로그인 안 된 상태라면 로그인 페이지로 리다이렉트
        response.sendRedirect("/login");
        return false;
    }
}
