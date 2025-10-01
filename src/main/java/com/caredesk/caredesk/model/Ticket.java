package com.caredesk.caredesk.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
@Data
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private Long assignedAgentId;

    @Column(nullable = false)
    private String subject;

    @Lob
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String content;

    @Column(nullable = false)
    private String status = "OPEN";  // Default: OPEN, IN_PROGRESS, CLOSED

    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private ArticleCategory category;  // Reuse existing category

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;  // Link to user
}