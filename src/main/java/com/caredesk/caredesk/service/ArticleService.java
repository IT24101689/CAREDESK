package com.caredesk.caredesk.service;

import com.caredesk.caredesk.model.Article;
import com.caredesk.caredesk.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public Article findById(Long id) {
        Optional<Article> optionalArticle = articleRepository.findById(id);
        if (optionalArticle.isPresent()) {
            return optionalArticle.get();
        } else {
            throw new RuntimeException("Article not found with id: " + id);
        }
    }

    public Article save(Article article) {
        return articleRepository.save(article);
    }

    public void deleteById(Long id) {
        articleRepository.deleteById(id);
    }

    // Search by title
    public List<Article> searchByTitle(String query) {
        if (query == null || query.isEmpty()) {
            return findAll();
        }
        return articleRepository.findByTitleContainingIgnoreCase(query);
    }
}
