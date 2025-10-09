package com.caredesk.caredesk.repository;

import com.caredesk.caredesk.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByCustomerId(Long customerId);
    //List<Ticket> findByAgent_Id(Long agentId); // For user's tickets
}