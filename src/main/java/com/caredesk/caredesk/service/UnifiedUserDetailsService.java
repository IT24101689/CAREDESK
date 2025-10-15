package com.caredesk.caredesk.service;

import com.caredesk.caredesk.repository.AdminRepository;
import com.caredesk.caredesk.repository.AgentRepository;
import com.caredesk.caredesk.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UnifiedUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AgentRepository agentRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Try to find as an Admin
        System.out.println("1. Checking for Admin...");
        UserDetails user = adminRepository.findByUsername(username).orElse(null);
        if (user != null) {
            System.out.println("Found user as an Admin.");
            return user;
        }

        // 2. Try to find as a Customer
        System.out.println("2. Checking for Customer...");
        user = customerRepository.findByEmail(username).orElse(null);
        if (user != null) {
            System.out.println("Found user as a Customer.");
            return user;
        }
      // 3. Try to find as an Agent
        System.out.println("3. Checking for Agent...");
        user = agentRepository.findByName(username).orElse(null);
        if (user != null) {
            System.out.println("Found user as an Agent.");
            return user;
        }

        // If no user is found, throw the exception
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}