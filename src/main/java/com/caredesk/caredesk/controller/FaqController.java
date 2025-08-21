package com.caredesk.caredesk.controller;

import com.caredesk.caredesk.model.Article;
import com.caredesk.caredesk.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class FaqController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/faq")
    public String showFaqPage(Model model, @RequestParam(required = false) String query) {
        List<Article> articles = articleService.searchByTitle(query);
        model.addAttribute("articles", articles);
        model.addAttribute("query", query); // Keep search input populated
        return "faq";
    }

    // ✅ Mapping for Read More
    @GetMapping("/faq/{id}")
    public String showArticleDetail(@PathVariable Long id, Model model) {
        Article article = articleService.findById(id);
        if (article == null) {
            return "redirect:/faq"; // fallback if not found
        }
        model.addAttribute("article", article);
        return "article_detail"; // Thymeleaf template
    }
}
