package com.example.subscriptionservice.controller;

import com.example.subscriptionservice.domain.Subscription;
import com.example.subscriptionservice.domain.SubscriptionProduct;
import com.example.subscriptionservice.repository.SubscriptionProductRepository;
import com.example.subscriptionservice.service.SubscriptionService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private final SubscriptionProductRepository productRepository;
    private final SubscriptionService subscriptionService;

    // 구독 신청 폼 보여주기 (상품 상세 → 신청 버튼 누르면 이동)
    @GetMapping("/subscribe/{productId}")
    public String showSubscribeForm(@PathVariable Long productId, Model model) {
        SubscriptionProduct product = productRepository.findById(productId)
                .orElse(null);

        if (product == null) {
            return "redirect:/subscriptions"; // 상품 없으면 목록으로
        }

        model.addAttribute("product", product);
        return "subscribe"; // templates/subscribe.html
    }

    // 구독 신청 처리 (POST)
    @PostMapping("/subscribe")
    public String handleSubscribe(@RequestParam Long productId,
                                  HttpSession session,
                                  Model model) {
        Object loggedInUser = session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login"; // 로그인 필요
        }

        SubscriptionProduct product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            model.addAttribute("error", "존재하지 않는 상품입니다.");
            return "subscribe";
        }

        // 구독 생성 로직 자리
        // subscriptionService.createSubscription(loggedInUserId, productId);
        // 현재는 mock 처리
        model.addAttribute("message", "구독이 성공적으로 처리되었습니다.");
        return "subscribe_success"; // templates/subscribe_success.html
    }

    // REST API: 구독 상태 변경 관련

    // 구독 활성화
    @PostMapping("/{id}/activate")
    @ResponseBody
    public ResponseEntity<Subscription> activate(@PathVariable Long id) {
        Subscription updated = subscriptionService.activateSubscription(id);
        return ResponseEntity.ok(updated);
    }

    // 구독 일시정지
    @PostMapping("/{id}/pause")
    @ResponseBody
    public ResponseEntity<Void> pause(@PathVariable Long id) {
        subscriptionService.pauseSubscription(id);
        return ResponseEntity.ok().build();
    }

    // 구독 취소
    @PostMapping("/{id}/cancel")
    @ResponseBody
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        subscriptionService.cancelSubscription(id);
        return ResponseEntity.ok().build();
    }

    // 자동 갱신 여부 on/off
    @PostMapping("/{id}/renewal")
    @ResponseBody
    public ResponseEntity<Subscription> toggleRenewal(@PathVariable Long id,
                                                      @RequestParam boolean autoRenew) {
        Subscription updated = subscriptionService.setAutoRenew(id, autoRenew);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/my-subscriptions")
    public String showMySubscriptions(HttpSession session, Model model) {
        Object loggedInUser = session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        Long userId = (Long) loggedInUser;

        List<Subscription> subscriptions = subscriptionService.getSubscriptionsByUserId(userId);
        model.addAttribute("subscriptions", subscriptions);

        return "my_subscriptions";
    }


}
