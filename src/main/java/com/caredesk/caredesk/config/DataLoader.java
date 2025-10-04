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

    //@Override
   // public void run(String... args) throws Exception {
       // if (agentRepository.count() == 0) {
           // Agent agent1 = new Agent();
           // agent1.setUsername("agent1");
           // agent1.setPassword("agentpass123"); // Hash in production
            //agent1.setEmail("agent1@caredesk.com");
            //agent1.setFirstName("John");
            //agent1.setLastName("Doe");
            //agent1.setPhoneNumber("123-456-7890");
            //agentRepository.save(agent1);

            //Agent agent2 = new Agent();
            //agent2.setUsername("agent2");
            //agent2.setPassword("agentpass456");
           // agent2.setEmail("agent2@caredesk.com");
           // agent2.setFirstName("Jane");
           // agent2.setLastName("Smith");
           // agent2.setPhoneNumber("987-654-3210");
           // agentRepository.save(agent2);

}