package com.caredesk.caredesk.repository;

import com.caredesk.caredesk.model.ArticleCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; // Import this

@Repository
public interface ArticleCategoryRepository extends JpaRepository<ArticleCategory, Long> {
    // --- ADD THIS METHOD ---
    // This allows the service to check for duplicates
    Optional<ArticleCategory> findByName(String name);
}