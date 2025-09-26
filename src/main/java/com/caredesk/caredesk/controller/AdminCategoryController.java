package com.caredesk.caredesk.controller;

import com.caredesk.caredesk.model.ArticleCategory;
import com.caredesk.caredesk.service.ArticleCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    @Autowired
    private ArticleCategoryService categoryService;

    // List all categories (table view)
    @GetMapping
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "admin_categories";  // New template for category table
    }

    // Show form to create new category
    @GetMapping("/new")
    public String showNewCategoryForm(Model model) {
        model.addAttribute("category", new ArticleCategory());
        return "category_form";  // New form template
    }

    // Save new category
    @PostMapping
    public String saveCategory(@ModelAttribute ArticleCategory category) {
        categoryService.save(category);
        return "redirect:/admin/categories";
    }

    // Show form to edit existing category
    @GetMapping("/edit/{id}")
    public String showEditCategoryForm(@PathVariable Long id, Model model) {
        ArticleCategory category = categoryService.findById(id);
        if (category == null) {
            return "redirect:/admin/categories";  // Fallback if not found
        }
        model.addAttribute("category", category);
        return "category_form";
    }

    // Update category
    @PostMapping("/update/{id}")
    public String updateCategory(@PathVariable Long id, @ModelAttribute ArticleCategory category) {
        category.setId(id);
        categoryService.save(category);
        return "redirect:/admin/categories";
    }

    // Delete category
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            categoryService.deleteById(id);
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());  // Show error if in use
        }
        return "redirect:/admin/categories";
    }
}