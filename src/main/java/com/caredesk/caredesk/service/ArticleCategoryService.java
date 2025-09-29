package com.caredesk.caredesk.service;

import com.caredesk.caredesk.model.ArticleCategory;
import com.caredesk.caredesk.repository.ArticleCategoryRepository;
import com.caredesk.caredesk.repository.ArticleRepository;  // Add for delete check
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleCategoryService {

    @Autowired
    private ArticleCategoryRepository categoryRepository;

    @Autowired
    private ArticleRepository articleRepository;  // For checking if category in use

    public List<ArticleCategory> findAll() {
        return categoryRepository.findAll();
    }

    public ArticleCategory findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public ArticleCategory save(ArticleCategory category) {
        return categoryRepository.save(category);
    }

    public void deleteById(Long id) {
        // Check if category is used in any articles
        if (articleRepository.existsByCategoryId(id)) {
            throw new RuntimeException("Cannot delete category; it is used in articles.");
        }
        categoryRepository.deleteById(id);
    }

    public boolean existsByCategoryId(Long id) {
        return articleRepository.existsByCategoryId(id);  // For error handling in controller
    }
}