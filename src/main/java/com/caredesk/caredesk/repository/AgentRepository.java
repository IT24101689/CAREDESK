package com.caredesk.caredesk.repository;

import com.caredesk.caredesk.model.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; // Add this import

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {
    // ADD THIS METHOD to find an agent by their login name
    Optional<Agent> findByName(String name);
}