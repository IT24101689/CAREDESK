package com.caredesk.caredesk.service;

import com.caredesk.caredesk.model.Customer;
import com.caredesk.caredesk.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService { // No longer implements UserDetailsService
    @Autowired
    private CustomerRepository customerRepository;

    // The loadUserByUsername method is removed as it's handled by UnifiedUserDetailsService.

    // This save method should also encode passwords if you use it for registration.
    // For now, it's unused if DataLoader is your only source of users.
    public Customer saveCustomer(Customer customer) {
        // You would add password encoding here if you build a registration form.
        return customerRepository.save(customer);
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }
}