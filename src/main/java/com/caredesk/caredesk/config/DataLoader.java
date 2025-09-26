package com.caredesk.caredesk.config;

import com.caredesk.caredesk.model.Admin;
import com.caredesk.caredesk.model.Customer;
import com.caredesk.caredesk.repository.AdminRepository;
import com.caredesk.caredesk.repository.CustomerRepository;
import com.caredesk.caredesk.service.CustomerService;
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

    @Autowired
    private PasswordEncoder passwordEncoder;  // Not used now, but keep for future

    @Autowired
    private CustomerService customerService;

    @Override
    public void run(String... args) throws Exception {
        // Optional: Clear existing data to force re-seed (comment out after first run)
        // adminRepository.deleteAll();
        // customerRepository.deleteAll();

        if (adminRepository.count() == 0) {
            Admin admin = new Admin();
            admin.setUsername("admin");
            admin.setPassword("123");  // Plain text
            adminRepository.save(admin);
            System.out.println("Default admin created: username=admin, password=123 (plain)");
        }

        if (customerRepository.count() == 0) {
            Customer customer = new Customer();
            customer.setName("John Doe");
            customer.setAddress("123 Main St");
            customer.setMobileNo("1234567890");
            customer.setEmail("john@example.com");
            customer.setPassword("123");  // Plain text
            customerRepository.save(customer);  // Direct save, no encode
            System.out.println("Default customer created: email=john@example.com, password=123 (plain)");
        }
    }
}