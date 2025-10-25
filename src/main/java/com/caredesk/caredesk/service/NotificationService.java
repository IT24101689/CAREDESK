package com.caredesk.caredesk.service;

import com.caredesk.caredesk.model.Customer;
import com.caredesk.caredesk.model.Notification;
import com.caredesk.caredesk.model.Ticket;

import java.util.List;

public interface NotificationService {

    void createNotification(Customer customer, Ticket ticket, String message);

    List<Notification> getNotificationsForCustomer(Customer customer);

    long getUnreadNotificationCount(Customer customer);

    Notification findByIdAndCustomer(Long id, Customer customer);

    void markAsRead(Notification notification);

    void deleteNotification(Long id, Customer customer);

    /**
     * Creates a notification manually, typically triggered by an admin.
     * @param customerId The ID of the customer to notify.
     * @param message The notification message content.
     */
    void createManualNotification(Long customerId, String message);
}
