package com.caredesk.caredesk.repository;

import com.caredesk.caredesk.model.Ticket;
import com.caredesk.caredesk.model.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByCustomerId(Long customerId);
    void deleteByCustomerId(Long customerId);
    List<Ticket> findByAssignedAgent(Agent agent);
    //List<Ticket> findByAgent_Id(Long agentId); // For user's tickets
    // --- ADD THIS METHOD ---
    // This query finds all tickets where the 'assignedAgent' field is null
    List<Ticket> findByAssignedAgentIsNull();

}