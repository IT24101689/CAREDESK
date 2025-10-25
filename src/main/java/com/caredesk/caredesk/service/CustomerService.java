package com.caredesk.caredesk.service;

import com.caredesk.caredesk.model.Customer;
import com.caredesk.caredesk.repository.CustomerRepository;
import com.caredesk.caredesk.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    // --- ADD THESE ---
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TicketRepository ticketRepository;

    // --- UPDATED THIS METHOD ---
    @Transactional
    public Customer saveCustomer(Customer customer) {
        // Check if email already exists
        if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new RuntimeException("An account with this email already exists.");
        }
        // Encode the password
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        return customerRepository.save(customer);
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    // --- ADD THIS METHOD ---
    @Transactional
    public void deleteCustomerById(Long customerId) {
        // 1. Delete all tickets associated with the customer
        //    (This avoids foreign key constraint errors)
        ticketRepository.deleteByCustomerId(customerId);

        // 2. Delete the customer
        customerRepository.deleteById(customerId);
    }
}