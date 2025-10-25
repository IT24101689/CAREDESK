package com.caredesk.caredesk.controller;

import com.caredesk.caredesk.model.Agent;
import com.caredesk.caredesk.model.Article;
import com.caredesk.caredesk.model.Ticket;
import com.caredesk.caredesk.service.AgentService;
import com.caredesk.caredesk.service.ArticleService;
import com.caredesk.caredesk.service.TicketService; // Import TicketService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ArticleService articleService;

    // --- INJECT THE NEW SERVICES ---
    @Autowired
    private TicketService ticketService;

    @Autowired
    private AgentService agentService;

    // --- UPDATE THIS METHOD ---
    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        // 1. Get articles (existing)
        List<Article> articles = articleService.findAll();
        model.addAttribute("articles", articles);

        // 2. Get unassigned tickets (new)
        List<Ticket> unassignedTickets = ticketService.findUnassignedTickets();
        model.addAttribute("unassignedTickets", unassignedTickets);

        // 3. Get all agents for the dropdown (new)
        List<Agent> agents = agentService.findAllAgents();
        model.addAttribute("agents", agents);

        return "admin_dashboard";
    }

    // --- ADD THIS NEW METHOD TO HANDLE ASSIGNMENT ---
    @PostMapping("/tickets/assign")
    public String assignTicket(@RequestParam Long ticketId,
                               @RequestParam Long agentId,
                               RedirectAttributes redirectAttributes) {
        try {
            // We use the AgentService method we created earlier
            agentService.assignTicketToAgent(ticketId, agentId);
            redirectAttributes.addFlashAttribute("successMessage", "Ticket #" + ticketId + " assigned successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error assigning ticket: " + e.getMessage());
        }
        return "redirect:/admin/dashboard";
    }
}