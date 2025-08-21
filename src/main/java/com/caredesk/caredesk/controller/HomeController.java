package com.caredesk.caredesk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String landingPage() {
        return "landing"; // maps to landing.html
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // maps to login.html
    }
}
