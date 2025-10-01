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
