package com.caredesk.caredesk.service;

import com.caredesk.caredesk.model.ArticleCategory;
import com.caredesk.caredesk.repository.ArticleCategoryRepository;
import com.caredesk.caredesk.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleCategoryService {

    @Autowired
    private ArticleCategoryRepository categoryRepository;

    @Autowired
    private ArticleRepository articleRepository; // Used for the delete check

    // --- ADD THIS METHOD ---
    public List<ArticleCategory> findAll() {
        return categoryRepository.findAll();
    }

    // --- ADD THIS METHOD ---
    public ArticleCategory findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }

    // --- YOUR EXISTING SAVE METHOD (which is correct) ---
    public ArticleCategory save(ArticleCategory category) {
        Optional<ArticleCategory> existingCategory = categoryRepository.findByName(category.getName());

        if (existingCategory.isPresent()) {
            if (category.getId() == null || !category.getId().equals(existingCategory.get().getId())) {
                throw new RuntimeException("A category with the name '" + category.getName() + "' already exists.");
            }
        }
        return categoryRepository.save(category);
    }

    // --- ADD THIS METHOD ---
    public void deleteById(Long id) {
        // Check if any articles are using this category
        boolean inUse = articleRepository.existsByCategoryId(id);
        if (inUse) {
            throw new RuntimeException("Cannot delete category: it is currently in use by one or more articles.");
        }
        categoryRepository.deleteById(id);
    }
}