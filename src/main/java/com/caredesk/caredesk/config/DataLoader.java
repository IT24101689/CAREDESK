package com.caredesk.caredesk.config;

import com.caredesk.caredesk.model.Admin;
import com.caredesk.caredesk.model.Customer;
import com.caredesk.caredesk.repository.AdminRepository;
import com.caredesk.caredesk.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private CustomerRepository customerRepository;

    // This is now correctly used to encode passwords.
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (adminRepository.count() == 0) {
            Admin admin = new Admin();
            admin.setUsername("admin");
            // ✅ CORRECT: Encode the password before saving
            admin.setPassword(passwordEncoder.encode("123"));
            admin.setRole("ADMIN");
            admin.setEmail("admin@example.com");
            adminRepository.save(admin);
            System.out.println("Default admin created with ENCODED password.");
        }

        if (customerRepository.count() == 0) {
            Customer customer = new Customer();
            customer.setName("John Doe");
            customer.setAddress("123 Main St");
            customer.setMobileNo("1234567890");
            customer.setEmail("john@example.com");
            // ✅ CORRECT: Encode the password before saving
            customer.setPassword(passwordEncoder.encode("123"));
            customerRepository.save(customer);
            System.out.println("Default customer created with ENCODED password.");
        }
    }
}