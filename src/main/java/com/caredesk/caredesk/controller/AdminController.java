package com.caredesk.caredesk.controller;

import com.caredesk.caredesk.model.Article;
import com.caredesk.caredesk.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ArticleService articleService;  // Add to load articles

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        List<Article> articles = articleService.findAll();
        model.addAttribute("articles", articles);  // Load articles for table
        return "admin_dashboard";
    }
}