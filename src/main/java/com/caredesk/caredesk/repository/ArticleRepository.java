package com.caredesk.caredesk.repository;

import com.caredesk.caredesk.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByTitleContainingIgnoreCase(String title);
    boolean existsByCategoryId(Long categoryId);

    // Updated: Cast content to STRING for LOWER
    @Query("SELECT a FROM Article a WHERE LOWER(a.title) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(CAST(a.content AS STRING)) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Article> searchByTitleOrContent(@Param("query") String query);
}