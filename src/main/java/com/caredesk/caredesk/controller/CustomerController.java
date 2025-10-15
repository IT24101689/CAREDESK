package com.caredesk.caredesk.controller;

import com.caredesk.caredesk.model.Customer;
import com.caredesk.caredesk.model.Ticket;
import com.caredesk.caredesk.service.ArticleCategoryService;
import com.caredesk.caredesk.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    // You no longer need CustomerService here for fetching the user
    // @Autowired
    // private CustomerService customerService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private ArticleCategoryService categoryService;

    // Use @AuthenticationPrincipal to get the logged-in customer directly
    @GetMapping("/dashboard")
    public String dashboard(Model model, @AuthenticationPrincipal Customer customer) {
        model.addAttribute("customer", customer);
        return "customer_dashboard";
    }

    // Use @AuthenticationPrincipal here as well
    @GetMapping("/tickets")
    public String listTickets(Model model, @AuthenticationPrincipal Customer customer) {
        model.addAttribute("tickets", ticketService.findByCustomerId(customer.getId()));
        return "tickets";
    }

    // This method doesn't need to change
    @GetMapping("/tickets/new")
    public String showNewTicketForm(Model model) {
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("categories", categoryService.findAll());
        return "ticket_form";
    }

    // Use @AuthenticationPrincipal to set the ticket's owner
    @PostMapping("/tickets")
    public String saveTicket(@ModelAttribute Ticket ticket, @AuthenticationPrincipal Customer customer) {
        ticket.setCustomer(customer);
        ticketService.save(ticket);
        return "redirect:/customer/tickets";
    }

    // Use @AuthenticationPrincipal for the security check
    @GetMapping("/tickets/{id}")
    public String viewTicket(@PathVariable Long id, Model model, @AuthenticationPrincipal Customer customer) {
        Ticket ticket = ticketService.findById(id);

        // Security Check is now cleaner
        if (ticket == null || !ticket.getCustomer().getId().equals(customer.getId())) {
            return "redirect:/customer/tickets";
        }

        model.addAttribute("ticket", ticket);
        return "ticket_detail";
    }

    // Use @AuthenticationPrincipal for the security check
    @GetMapping("/tickets/delete/{id}")
    public String deleteTicket(@PathVariable Long id, @AuthenticationPrincipal Customer customer) {
        Ticket ticket = ticketService.findById(id);

        if (ticket != null && ticket.getCustomer().getId().equals(customer.getId())) {
            ticketService.deleteById(id);
        }

        return "redirect:/customer/tickets";
    }
}