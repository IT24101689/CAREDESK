package com.caredesk.caredesk.controller;

import com.caredesk.caredesk.model.Ticket;
import com.caredesk.caredesk.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/agent")
public class AgentController {

    @Autowired
    private TicketRepository ticketRepository;

    // View all tickets assigned to the agent
    @GetMapping("/tickets")
    public ResponseEntity<List<Ticket>> getAssignedTickets(@RequestParam Long agentId) {
        List<Ticket> tickets = ticketRepository.findByAgent_Id(agentId);
        return ResponseEntity.ok(tickets);
    }

    // Update ticket status
    @PostMapping("/updateTicketStatus")
    public ResponseEntity<Ticket> updateTicketStatus(@RequestParam Long ticketId, @RequestParam String status) {
        Optional<Ticket> ticketOpt = ticketRepository.findById(ticketId);
        return ticketOpt.map(ticket -> {
            ticket.setStatus(status); // Validate status (e.g., OPEN, IN_PROGRESS, CLOSED) if needed
            return ResponseEntity.ok(ticketRepository.save(ticket));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}