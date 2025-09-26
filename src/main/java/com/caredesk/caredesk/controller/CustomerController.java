package com.caredesk.caredesk.controller;

import com.caredesk.caredesk.model.Customer;
import com.caredesk.caredesk.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        String email = authentication.getName();  // Logged-in user's email
        Customer customer = (Customer) customerService.loadUserByUsername(email);
        model.addAttribute("customer", customer);
        return "customer_dashboard";  // Thymeleaf template
    }

    @GetMapping("/profile")
    public String profile(Model model, Authentication authentication) {
        String email = authentication.getName();
        Customer customer = (Customer) customerService.loadUserByUsername(email);
        model.addAttribute("customer", customer);
        return "customer_profile";  // Separate profile template if needed
    }
}