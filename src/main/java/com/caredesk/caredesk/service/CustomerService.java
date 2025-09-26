package com.caredesk.caredesk.service;

import com.caredesk.caredesk.model.Customer;
import com.caredesk.caredesk.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService implements UserDetailsService {  // For customer authentication
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;  // From SecurityConfig

    // Load user for login
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Customer not found: " + email));
    }

    // Example: Save a new customer (use for registration if needed)
    public Customer saveCustomer(Customer customer) {
        customer.setPassword(customer.getPassword());  // Plain text; no encode
        return customerRepository.save(customer);
    }

    // Get customer by ID (for profile)
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }
}