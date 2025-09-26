package com.caredesk.caredesk.controller;

import com.caredesk.caredesk.model.ArticleCategory;
import com.caredesk.caredesk.model.Customer;
import com.caredesk.caredesk.model.Ticket;
import com.caredesk.caredesk.service.ArticleCategoryService;
import com.caredesk.caredesk.service.CustomerService;
import com.caredesk.caredesk.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private ArticleCategoryService categoryService;  // For dropdown

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        String email = authentication.getName();
        Customer customer = (Customer) customerService.loadUserByUsername(email);
        model.addAttribute("customer", customer);
        return "customer_dashboard";
    }

    // List user's tickets
    @GetMapping("/tickets")
    public String listTickets(Model model, Authentication authentication) {
        String email = authentication.getName();
        Customer customer = (Customer) customerService.loadUserByUsername(email);
        model.addAttribute("tickets", ticketService.findByCustomerId(customer.getId()));
        return "tickets";  // New template
    }

    // Show create form
    @GetMapping("/tickets/new")
    public String showNewTicketForm(Model model) {
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("categories", categoryService.findAll());
        return "ticket_form";  // New template
    }

    // Save new ticket
    @PostMapping("/tickets")
    public String saveTicket(@ModelAttribute Ticket ticket, Authentication authentication) {
        String email = authentication.getName();
        Customer customer = (Customer) customerService.loadUserByUsername(email);
        ticket.setCustomer(customer);
        ticketService.save(ticket);
        return "redirect:/customer/tickets";
    }

    // View ticket details
    @GetMapping("/tickets/{id}")
    public String viewTicket(@PathVariable Long id, Model model, Authentication authentication) {
        Ticket ticket = ticketService.findById(id);
        if (ticket == null || !ticket.getCustomer().getEmail().equals(authentication.getName())) {
            return "redirect:/customer/tickets";  // Security: own tickets only
        }
        model.addAttribute("ticket", ticket);
        return "ticket_detail";  // New template
    }

    // Delete ticket
    @GetMapping("/tickets/delete/{id}")
    public String deleteTicket(@PathVariable Long id, Authentication authentication) {
        Ticket ticket = ticketService.findById(id);
        if (ticket != null && ticket.getCustomer().getEmail().equals(authentication.getName())) {
            ticketService.deleteById(id);
        }
        return "redirect:/customer/tickets";
    }
}