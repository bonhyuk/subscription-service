package com.example.subscriptionservice.controller;

import com.example.subscriptionservice.domain.Subscription;
import com.example.subscriptionservice.domain.SubscriptionProduct;
import com.example.subscriptionservice.domain.SubscriptionStatus;
import com.example.subscriptionservice.domain.User;
import com.example.subscriptionservice.repository.SubscriptionProductRepository;
import com.example.subscriptionservice.repository.UserRepository;
import com.example.subscriptionservice.service.SubscriptionService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

/**
 * 구독 관련 요청을 처리하는 컨트롤러 클래스
 * - 구독 신청 폼 보여주기
 * - 구독 신청 처리
 * - 구독 상태 변경 (활성화, 일시정지, 취소 등)
 * - 로그인한 사용자의 구독 내역 조회
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private final SubscriptionProductRepository subscriptionProductRepository; // 구독 상품 데이터 접근
    private final SubscriptionService subscriptionService;         // 구독 관련 비즈니스 로직
    private final UserRepository userRepository;                   // 사용자 데이터 접근

    /**
     * 구독 신청 폼 화면으로 이동 (상품 상세 페이지에서 신청 버튼 클릭 시)
     * @param productId 신청할 상품 ID
     * @param model 뷰에 데이터 전달용
     * @return 구독 신청 폼 뷰 이름
     */
    @GetMapping("/subscribe/{productId}")
    public String showSubscribeForm(@PathVariable Long productId, Model model) {
        SubscriptionProduct product = subscriptionProductRepository.findById(productId)
                .orElse(null);

        if (product == null) {
            return "redirect:/subscriptions"; // 상품이 없으면 구독 상품 목록으로 리다이렉트
        }

        model.addAttribute("product", product);
        return "subscribe"; // subscribe.html 뷰 렌더링
    }

    /**
     * 구독 신청 처리 (POST)
     * @param productId 신청할 상품 ID
     * @param session 세션에서 로그인 사용자 확인용
     * @param model 뷰에 데이터 전달용
     * @return 신청 성공 시 성공 페이지, 실패 시 다시 신청 페이지
     */
    @PostMapping("/subscribe")
    public String handleSubscribe(@RequestParam Long productId,
                                  HttpSession session,
                                  Model model) {
        Object loggedInUser = session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login"; // 로그인 안된 경우 로그인 페이지로
        }

        SubscriptionProduct product = subscriptionProductRepository.findById(productId).orElse(null);
        if (product == null) {
            model.addAttribute("error", "존재하지 않는 상품입니다.");
            return "subscribe"; // 상품 없으면 다시 폼으로
        }

        User user = (User) loggedInUser;
        Long userId = user.getId();

        subscriptionService.createSubscription(userId, productId); // 실제 구독 저장 처리 호출

        model.addAttribute("message", "구독이 성공적으로 처리되었습니다.");
        return "subscribe_success"; // 구독 성공 페이지
    }

    // --- 구독 상태 변경 REST API ---

    /**
     * 구독 활성화
     * @param id 구독 ID
     * @return 활성화된 구독 정보
     */
    @PostMapping("/{id}/activate")
    @ResponseBody
    public ResponseEntity<Subscription> activate(@PathVariable Long id) {
        Subscription updated = subscriptionService.activateSubscription(id);
        return ResponseEntity.ok(updated);
    }

    /**
     * 구독 일시정지
     * @param id 구독 ID
     * @return 성공 여부 (빈 응답)
     */
    @PostMapping("/{id}/pause")
    @ResponseBody
    public ResponseEntity<Void> pause(@PathVariable Long id) {
        subscriptionService.pauseSubscription(id);
        return ResponseEntity.ok().build();
    }

    /**
     * 구독 취소
     * @param id 구독 ID
     * @return 성공 여부 (빈 응답)
     */
    @PostMapping("/{id}/cancel")
    @ResponseBody
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        subscriptionService.cancelSubscription(id);
        return ResponseEntity.ok().build();
    }

    /**
     * 자동 갱신 여부 토글
     * @param id 구독 ID
     * @param autoRenew 자동 갱신 여부
     * @return 변경된 구독 정보
     */
    @PostMapping("/{id}/renewal")
    @ResponseBody
    public ResponseEntity<Subscription> toggleRenewal(@PathVariable Long id,
                                                      @RequestParam boolean autoRenew) {
        Subscription updated = subscriptionService.setAutoRenew(id, autoRenew);
        return ResponseEntity.ok(updated);
    }

    /**
     * 로그인한 사용자의 구독 내역 화면 조회 (Spring Security Principal 방식)
     * @param model 뷰에 데이터 전달용
     * @param principal Spring Security 인증된 사용자 정보
     * @return 구독 내역 페이지 뷰 이름
     */
    @GetMapping("/my")
    public String viewMySubscriptions(HttpSession session, Model model) {
        // 세션에서 로그인한 사용자 정보 가져오기
        Object loggedInUser = session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login"; // 로그인 안된 경우 로그인 페이지로 리다이렉트
        }

        User user = (User) loggedInUser;

        // 👉 로그 출력
        System.out.println("username = " + user.getUsername());
        System.out.println("userId = " + user.getId());

        List<Subscription> mySubscriptions = subscriptionService.getSubscriptionsByUserId(user.getId());

        // 👉 구독 개수 로그
        System.out.println("구독 개수 = " + mySubscriptions.size());

        LocalDate today = LocalDate.now();
        for (Subscription s : mySubscriptions) {
            if (s.getEndDate() != null && s.getEndDate().isBefore(today)) {
                s.setStatus(SubscriptionStatus.EXPIRED);
            } else if (s.getStatus() != SubscriptionStatus.CANCELLED &&
                    s.getStatus() != SubscriptionStatus.PAUSED) {
                s.setStatus(SubscriptionStatus.ACTIVE);
            }
        }

        model.addAttribute("subscriptions", mySubscriptions);
        return "my_subscriptions";
    }


//    @GetMapping("/my")
//    public String viewMySubscriptions(Model model, Principal principal) {
//        String username = principal.getName();
//
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
//
//        // 👉 로그 출력 (여기!)
//        System.out.println("username = " + username);
//        System.out.println("userId = " + user.getId());
//
//        List<Subscription> mySubscriptions = subscriptionService.getSubscriptionsByUserId(user.getId());
//
//        // 👉 구독 개수 로그
//        System.out.println("구독 개수 = " + mySubscriptions.size());
//
//        LocalDate today = LocalDate.now();
//        for (Subscription s : mySubscriptions) {
//            if (s.getEndDate() != null && s.getEndDate().isBefore(today)) {
//                s.setStatus(SubscriptionStatus.EXPIRED);
//            } else if (s.getStatus() != SubscriptionStatus.CANCELLED &&
//                    s.getStatus() != SubscriptionStatus.PAUSED) {
//                s.setStatus(SubscriptionStatus.ACTIVE);
//            }
//        }
//
//        model.addAttribute("subscriptions", mySubscriptions);
//        return "my_subscriptions";
//    }
}
