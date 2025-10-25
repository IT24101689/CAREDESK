package com.caredesk.caredesk.service;

import com.caredesk.caredesk.model.Customer;
import com.caredesk.caredesk.model.Notification;
import com.caredesk.caredesk.model.NotificationStatus;
import com.caredesk.caredesk.model.Ticket;
import com.caredesk.caredesk.repository.CustomerRepository;
import com.caredesk.caredesk.repository.NotificationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // <-- This annotation tells Spring to create this bean
public class NotificationServiceImpl implements NotificationService { // <-- It implements the interface

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void createNotification(Customer customer, Ticket ticket, String message) {
        Notification notification = new Notification();
        notification.setCustomer(customer);
        notification.setTicket(ticket);
        notification.setMessage(message);
        notification.setStatus(NotificationStatus.UNREAD);
        notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getNotificationsForCustomer(Customer customer) {
        return notificationRepository.findByCustomerOrderByCreatedAtDesc(customer);
    }

    @Override
    public long getUnreadNotificationCount(Customer customer) {
        return notificationRepository.countByCustomerAndStatus(customer, NotificationStatus.UNREAD);
    }

    @Override
    public Notification findByIdAndCustomer(Long id, Customer customer) {
        return notificationRepository.findById(id)
                .filter(notification -> notification.getCustomer().getId().equals(customer.getId()))
                .orElseThrow(() -> new EntityNotFoundException("Notification not found"));
    }

    @Override
    public void markAsRead(Notification notification) {
        notification.setStatus(NotificationStatus.READ);
        notificationRepository.save(notification);
    }

    @Override
    public void deleteNotification(Long id, Customer customer) {
        Notification notification = findByIdAndCustomer(id, customer);
        notificationRepository.delete(notification);
    }

    @Override
    public void createManualNotification(Long customerId, String message) {
        // 1. Find the customer
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with ID: " + customerId));

        // 2. Create the notification (no associated ticket for manual ones)
        Notification notification = new Notification();
        notification.setCustomer(customer);
        notification.setMessage(message);
        notification.setStatus(NotificationStatus.UNREAD);
        // Note: notification.setTicket(null); // Explicitly null or leave it null by default

        // 3. Save it
        notificationRepository.save(notification);
    }
}