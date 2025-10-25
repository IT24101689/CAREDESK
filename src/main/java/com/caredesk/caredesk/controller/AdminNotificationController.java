package com.caredesk.caredesk.controller;

import com.caredesk.caredesk.model.Customer;
import com.caredesk.caredesk.repository.CustomerRepository; // Import CustomerRepository
import com.caredesk.caredesk.service.NotificationService;
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
@RequestMapping("/admin/notifications")
public class AdminNotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private CustomerRepository customerRepository; // To get the customer list

    // Show the form to create a manual notification
    @GetMapping("/new")
    public String showCreateNotificationForm(Model model) {
        List<Customer> customers = customerRepository.findAll();
        model.addAttribute("customers", customers);
        return "admin/create-notification"; // Path to the new Thymeleaf template
    }

    // Process the form submission
    @PostMapping("/send")
    public String sendNotification(@RequestParam Long customerId,
                                   @RequestParam String message,
                                   RedirectAttributes redirectAttributes) {
        try {
            notificationService.createManualNotification(customerId, message);
            redirectAttributes.addFlashAttribute("successMessage", "Notification sent successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error sending notification: " + e.getMessage());
            // Optionally redirect back to the form on error
            // return "redirect:/admin/notifications/new";
        }
        // Redirect back to the main admin dashboard on success or simple error
        return "redirect:/admin/dashboard";
    }
}