package com.caredesk.caredesk.controller;

import com.caredesk.caredesk.model.Ticket;
import com.caredesk.caredesk.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/agent")
public class AgentController {

    @Autowired
    private TicketRepository ticketRepository;

    // Display agent dashboard with assigned tickets
    @GetMapping
    public String agentDashboard(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/admin/login"; // Redirect to login if not authenticated
        }
        // For now, assume agentId is 1 (replace with actual user ID from Principal in production)
        Long agentId = 1L; // Default for testing
        List<Ticket> tickets = ticketRepository.findByAgent_Id(agentId);
        model.addAttribute("tickets", tickets);
        model.addAttribute("agentId", agentId);
        model.addAttribute("agentUsername", principal.getName()); // Display logged-in username
        return "agentDashboard"; // Thymeleaf template name
    }

    // Update ticket status
    @PostMapping("/updateTicket")
    public String updateTicketStatus(@RequestParam Long ticketId, @RequestParam String status, @RequestParam Long agentId, Model model) {
        ticketRepository.findById(ticketId).ifPresent(ticket -> {
            ticket.setStatus(status);
            ticketRepository.save(ticket);
        });
        return "redirect:/agent?agentId=" + agentId; // Redirect to dashboard
    }
}