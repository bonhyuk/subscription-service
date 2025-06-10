
// SubscriptionProductController.java
// 경로: com.example.subscriptionservice.controller.SubscriptionProductController

package com.example.subscriptionservice.controller;

import com.example.subscriptionservice.repository.SubscriptionProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/subscriptions")
public class SubscriptionProductController {

    private final SubscriptionProductRepository productRepository;

    @GetMapping
    public String showSubscriptionProducts(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "subscriptions"; // templates/subscriptions.html 렌더링
    }
} 