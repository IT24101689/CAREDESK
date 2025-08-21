package com.caredesk.caredesk.service;

import com.caredesk.caredesk.model.ArticleCategory;
import com.caredesk.caredesk.repository.ArticleCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleCategoryService {

    private final ArticleCategoryRepository categoryRepository;

    public ArticleCategoryService(ArticleCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<ArticleCategory> findAll() {
        return categoryRepository.findAll();
    }
}
