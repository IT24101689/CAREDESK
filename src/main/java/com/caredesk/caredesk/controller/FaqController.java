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
        List<Article> articles = articleService.searchByQuery(query);  // Updated to new method
        model.addAttribute("articles", articles);
        model.addAttribute("query", query);
        return "faq";
    }

    @GetMapping("/faq/{id}")
    public String showArticleDetail(@PathVariable Long id, Model model) {
        Article article = articleService.findById(id);
        if (article == null) {
            return "redirect:/faq";
        }
        model.addAttribute("article", article);
        return "article_detail";
    }
}