package com.caredesk.caredesk.service;

import com.caredesk.caredesk.model.Agent;
import com.caredesk.caredesk.model.Ticket;
import com.caredesk.caredesk.model.TicketResponse;

import java.util.List;
import java.util.Optional;

public interface AgentTicketService {
    // Find tickets assigned to a specific agent
    List<Ticket> findTicketsByAgent(Agent agent);

    // Find a specific ticket by its ID
    Optional<Ticket> findTicketById(Long ticketId);

    // Update the status of a ticket (e.g., from OPEN to IN_PROGRESS)
    Ticket updateTicketStatus(Long ticketId, String status);

    // Add a new response to a ticket
    TicketResponse addResponseToTicket(String content, Long ticketId, Agent agent);
}