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
import java.util.List;

@Controller
@RequestMapping("/admin/articles")
public class AdminArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleCategoryService categoryService;

    @Autowired
    private AdminService adminService;

    // List all articles on admin dashboard
    @GetMapping
    public String listArticles(Model model) {
        List<Article> articles = articleService.findAll();
        model.addAttribute("articles", articles);
        return "admin_dashboard";  // dashboard page lists articles
    }

    // Show form to create a new article
    @GetMapping("/new")
    public String showNewArticleForm(Model model) {
        model.addAttribute("article", new Article());
        model.addAttribute("categories", categoryService.findAll());
        return "article_form";  // form page for add/edit
    }

    // Save new article
    @PostMapping
    public String saveArticle(@ModelAttribute Article article, Principal principal) {
        // Set the logged-in admin as creator
        Admin admin = adminService.findByUsername(principal.getName());
        article.setCreatedBy(admin);

        articleService.save(article);
        return "redirect:/admin/articles";
    }

    // Show form to edit existing article
    @GetMapping("/edit/{id}")
    public String showEditArticleForm(@PathVariable Long id, Model model) {
        Article article = articleService.findById(id);
        model.addAttribute("article", article);
        model.addAttribute("categories", categoryService.findAll());
        return "article_form";
    }

    // Update article (including setting the creator again)
    @PostMapping("/update/{id}")
    public String updateArticle(@PathVariable Long id, @ModelAttribute Article article, Principal principal) {
        article.setId(id);

        // Keep or update the creator admin - usually keep original creator, but here just set current admin for simplicity
        Admin admin = adminService.findByUsername(principal.getName());
        article.setCreatedBy(admin);

        articleService.save(article);
        return "redirect:/admin/articles";
    }

    // Delete article by ID
    @GetMapping("/delete/{id}")
    public String deleteArticle(@PathVariable Long id) {
        articleService.deleteById(id);
        return "redirect:/admin/articles";
    }
}
