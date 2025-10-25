package com.caredesk.caredesk.model;

import jakarta.persistence.*;
import java.util.List; // Import the List class

@Entity
@Table(name = "article_categories")
public class ArticleCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    // --- ADD THIS FIELD ---
    // This creates the other side of the relationship
    // 'mappedBy = "category"' tells JPA that the 'Article' entity owns this relationship
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Article> articles;

    public ArticleCategory() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    // --- ADD GETTERS AND SETTERS for the new field ---
    public List<Article> getArticles() { return articles; }
    public void setArticles(List<Article> articles) { this.articles = articles; }
}