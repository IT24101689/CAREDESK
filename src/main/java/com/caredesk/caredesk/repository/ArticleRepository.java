package com.caredesk.caredesk.repository;

import com.caredesk.caredesk.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByTitleContainingIgnoreCase(String title);
    boolean existsByCategoryId(Long categoryId);  // For delete check
}