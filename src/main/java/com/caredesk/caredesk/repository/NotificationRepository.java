package com.caredesk.caredesk.repository;

import com.caredesk.caredesk.model.Customer;
import com.caredesk.caredesk.model.Notification;
import com.caredesk.caredesk.model.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // Find all notifications for a customer, ordered by newest first
    List<Notification> findByCustomerOrderByCreatedAtDesc(Customer customer);

    // Find all notifications by status for the unread count
    List<Notification> findByCustomerAndStatus(Customer customer, NotificationStatus status);

    // Count unread notifications
    long countByCustomerAndStatus(Customer customer, NotificationStatus status);
}