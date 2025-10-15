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

    // No changes to these fields
    private String title;
    private String description;
    @Column(nullable = false)
    private String subject;
    @Lob
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String content;
    @Column(nullable = false)
    private String status = "OPEN";
    private LocalDateTime createdAt = LocalDateTime.now();

    // This properly links to the Agent entity for assignments.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_agent_id") // Matches your database screenshot
    private Agent assignedAgent;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private ArticleCategory category;  // Reuse existing category

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;  // Link to user

    @ManyToOne
    @JoinColumn(name = "admin_id") // Optional: Tracks admin oversight
    private Admin admin; // Link to admin for management
}