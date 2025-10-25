package com.caredesk.caredesk.controller;

import com.caredesk.caredesk.model.Customer;
import com.caredesk.caredesk.model.Notification;
import com.caredesk.caredesk.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/customer/notifications")
    public String showNotifications(Model model, @AuthenticationPrincipal Customer customer) {
        List<Notification> notifications = notificationService.getNotificationsForCustomer(customer);
        model.addAttribute("notifications", notifications);
        return "notifications"; // Points to notifications.html
    }

    @GetMapping("/customer/notifications/view/{id}")
    public String viewNotification(@PathVariable Long id, @AuthenticationPrincipal Customer customer) {
        // Find the notification to ensure it belongs to the user
        Notification notification = notificationService.findByIdAndCustomer(id, customer);

        // Mark it as read
        notificationService.markAsRead(notification);

        // Redirect the user to the ticket this notification is about
        return "redirect:/customer/tickets/" + notification.getTicket().getId();
    }

    @GetMapping("/customer/notifications/delete/{id}")
    public String deleteNotification(@PathVariable Long id, @AuthenticationPrincipal Customer customer, RedirectAttributes redirectAttributes) {
        try {
            notificationService.deleteNotification(id, customer);
            redirectAttributes.addFlashAttribute("successMessage", "Notification deleted.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting notification.");
        }
        return "redirect:/customer/notifications";
    }
}