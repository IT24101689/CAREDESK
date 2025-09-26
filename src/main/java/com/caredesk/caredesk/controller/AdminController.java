package com.caredesk.caredesk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin/dashboard")
    public String showDashboard() {
        return "redirect:/admin/articles";  // Redirect dashboard to articles list
    }
}