package com.caredesk.caredesk.controller;

import com.caredesk.caredesk.model.Agent;
import com.caredesk.caredesk.model.Ticket;
import com.caredesk.caredesk.service.AgentTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/agent/tickets")
public class AgentTicketController {

    @Autowired
    private AgentTicketService agentTicketService;

    // Display the dashboard with tickets assigned to the logged-in agent
    @GetMapping
    public String viewAgentDashboard(Model model, @AuthenticationPrincipal Agent agent) {
        model.addAttribute("tickets", agentTicketService.findTicketsByAgent(agent));
        return "agent/dashboard";
    }

    // View the details of a specific ticket and add responses
    @GetMapping("/{ticketId}")
    public String viewTicketDetails(@PathVariable Long ticketId, Model model) {
        Ticket ticket = agentTicketService.findTicketById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        model.addAttribute("ticket", ticket);
        // We'll need to fetch and add responses here later
        return "agent/ticket-details";
    }

    // Add a new response to a ticket
    @PostMapping("/{ticketId}/responses")
    public String addResponse(@PathVariable Long ticketId,
                              @RequestParam String content,
                              @AuthenticationPrincipal Agent agent,
                              RedirectAttributes redirectAttributes) {
        try {
            agentTicketService.addResponseToTicket(content, ticketId, agent);
            // Also, update the ticket status to show it's being worked on
            agentTicketService.updateTicketStatus(ticketId, "IN_PROGRESS");
            redirectAttributes.addFlashAttribute("successMessage", "Response added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding response: " + e.getMessage());
        }
        return "redirect:/agent/tickets/" + ticketId;
    }

    // Update a ticket's status (e.g., to CLOSED)
    @PostMapping("/{ticketId}/status")
    public String updateStatus(@PathVariable Long ticketId,
                               @RequestParam String status,
                               RedirectAttributes redirectAttributes) {
        try {
            agentTicketService.updateTicketStatus(ticketId, status);
            redirectAttributes.addFlashAttribute("successMessage", "Ticket status updated to " + status);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating status: " + e.getMessage());
        }
        return "redirect:/agent/tickets/" + ticketId;
    }
}