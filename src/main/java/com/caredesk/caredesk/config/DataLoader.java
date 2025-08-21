package com.caredesk.caredesk.config;

import com.caredesk.caredesk.model.Admin;
import com.caredesk.caredesk.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public void run(String... args) throws Exception {
        if (adminRepository.count() == 0) {
            Admin admin = new Admin();
            admin.setUsername("admin");
            admin.setPassword("123"); // simple plaintext password for testing
            adminRepository.save(admin);
            System.out.println("Default admin created: username=admin, password=password123");
        }
    }
}
