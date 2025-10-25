package com.caredesk.caredesk.controller;

import com.caredesk.caredesk.model.Customer;
import com.caredesk.caredesk.model.Ticket;
import com.caredesk.caredesk.service.ArticleCategoryService;
import com.caredesk.caredesk.service.CustomerService;
import com.caredesk.caredesk.service.TicketService;
import com.caredesk.caredesk.service.NotificationService;;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ArticleCategoryService categoryService; // Needed for the 'new ticket' form

    // --- THIS IS THE CORRECTED DASHBOARD METHOD ---
    @GetMapping("/dashboard")
    public String dashboard(Model model, @AuthenticationPrincipal Customer customer) {
        model.addAttribute("customer", customer);
        model.addAttribute("unreadCount", notificationService.getUnreadNotificationCount(customer));
        return "customer_dashboard";
    }

    // --- ALL OTHER METHODS ALSO UPDATED TO USE @AuthenticationPrincipal ---

    @GetMapping("/tickets")
    public String listTickets(Model model, @AuthenticationPrincipal Customer customer) {
        model.addAttribute("tickets", ticketService.findByCustomerId(customer.getId()));
        return "tickets";
    }

    @GetMapping("/tickets/new")
    public String showNewTicketForm(Model model) {
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("categories", categoryService.findAll());
        return "ticket_form";
    }

    @PostMapping("/tickets")
    public String saveTicket(@ModelAttribute Ticket ticket, @AuthenticationPrincipal Customer customer) {
        ticket.setCustomer(customer);
        ticketService.save(ticket);
        return "redirect:/customer/tickets";
    }

    @GetMapping("/tickets/{id}")
    public String viewTicket(@PathVariable Long id, Model model, @AuthenticationPrincipal Customer customer) {
        Ticket ticket = ticketService.findById(id);

        if (ticket == null || !ticket.getCustomer().getId().equals(customer.getId())) {
            return "redirect:/customer/tickets";
        }

        model.addAttribute("ticket", ticket);
        return "ticket_detail";
    }

    @GetMapping("/tickets/delete/{id}")
    public String deleteTicket(@PathVariable Long id, @AuthenticationPrincipal Customer customer) {
        Ticket ticket = ticketService.findById(id);

        if (ticket != null && ticket.getCustomer().getId().equals(customer.getId())) {
            ticketService.deleteById(id);
        }

        return "redirect:/customer/tickets";
    }

    @GetMapping("/delete-account")
    public String deleteAccount(@AuthenticationPrincipal Customer customer, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            customerService.deleteCustomerById(customer.getId());

            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            SecurityContextHolder.clearContext();

            redirectAttributes.addFlashAttribute("successMessage", "Your account has been successfully deleted.");
            return "redirect:/";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting account. Please contact support.");
            return "redirect:/customer/dashboard";
        }
    }
}