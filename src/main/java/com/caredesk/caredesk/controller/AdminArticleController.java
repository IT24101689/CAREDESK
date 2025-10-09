package com.caredesk.caredesk.controller;

import com.caredesk.caredesk.model.Admin;
import com.caredesk.caredesk.model.Article;
import com.caredesk.caredesk.service.AdminService;
import com.caredesk.caredesk.service.ArticleCategoryService;
import com.caredesk.caredesk.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/admin/articles")
public class AdminArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleCategoryService categoryService;

    @Autowired
    private AdminService adminService;

    // List all articles – redirect to dashboard (which now loads articles)
    @GetMapping
    public String listArticles() {
        return "redirect:/admin/dashboard";
    }

    // Show form to create a new article
    @GetMapping("/new")
    public String showNewArticleForm(Model model) {
        model.addAttribute("article", new Article());
        model.addAttribute("categories", categoryService.findAll());
        return "article_form";
    }

    // Save new article
    @PostMapping
    public String saveArticle(@ModelAttribute Article article, Principal principal) {
        Admin admin = adminService.findByUsername(principal.getName());
        article.setCreatedBy(admin);
        articleService.save(article);
        return "redirect:/admin/dashboard";  // Back to dashboard with list
    }

    // Show form to edit existing article
    @GetMapping("/edit/{id}")
    public String showEditArticleForm(@PathVariable Long id, Model model) {
        Article article = articleService.findById(id);
        model.addAttribute("article", article);
        model.addAttribute("categories", categoryService.findAll());
        return "article_form";
    }

    // Update article
    @PostMapping("/update/{id}")
    public String updateArticle(@PathVariable Long id, @ModelAttribute Article article, Principal principal) {
        article.setId(id);
        Admin admin = adminService.findByUsername(principal.getName());
        article.setCreatedBy(admin);
        articleService.save(article);
        return "redirect:/admin/dashboard";
    }

    // Delete article by ID
    @GetMapping("/delete/{id}")
    public String deleteArticle(@PathVariable Long id) {
        articleService.deleteById(id);
        return "redirect:/admin/dashboard";
    }
}