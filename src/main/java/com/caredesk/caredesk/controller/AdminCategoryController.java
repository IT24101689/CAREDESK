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
        // Add attributes for success/error messages from redirects
        model.addAttribute("categories", categoryService.findAll());
        return "admin_categories";
    }

    // Show form to create new category
    @GetMapping("/new")
    public String showNewCategoryForm(Model model) {
        model.addAttribute("category", new ArticleCategory());
        return "category_form";
    }

    // Show form to edit existing category
    @GetMapping("/edit/{id}")
    public String showEditCategoryForm(@PathVariable Long id, Model model) {
        ArticleCategory category = categoryService.findById(id);
        if (category == null) {
            return "redirect:/admin/categories";
        }
        model.addAttribute("category", category);
        return "category_form";
    }

    // --- UPDATED & MERGED SAVE METHOD ---
    // Handles both creating new and updating existing categories
    @PostMapping("/save")
    public String saveCategory(@ModelAttribute ArticleCategory category, RedirectAttributes redirectAttributes) {
        try {
            // This now uses the service method that checks for duplicate names
            categoryService.save(category);
            redirectAttributes.addFlashAttribute("successMessage", "Category saved successfully!");

        } catch (RuntimeException e) {
            // --- CATCHES THE DUPLICATE NAME ERROR ---
            // Add the error message from the service to be displayed on the form
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());

            // Send the user back to the form they were on
            if (category.getId() == null) {
                return "redirect:/admin/categories/new";
            } else {
                return "redirect:/admin/categories/edit/" + category.getId();
            }
        }

        // On success, redirect to the main list
        return "redirect:/admin/categories";
    }

    // Delete category
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            // This uses the service method that checks if the category is in use
            categoryService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Category deleted successfully!");
        } catch (RuntimeException e) {
            // Show error if category is in use by an article
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/categories";
    }
}