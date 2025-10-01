package com.caredesk.caredesk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private TicketRepository ticketRepository;

    // Manage User (Update/Remove)
    @PostMapping("/manageUser")
    public ResponseEntity<Admin> manageUser(@RequestBody Admin admin) {
        Admin savedAdmin = adminRepository.save(admin);
        return ResponseEntity.ok(savedAdmin);
    }

    // Assign Roles
    @PostMapping("/assignRole")
    public ResponseEntity<Admin> assignRole(@RequestParam Long adminId, @RequestParam String role) {
        Optional<Admin> adminOpt = adminRepository.findById(adminId);
        return adminOpt.map(admin -> {
            admin.setRole(role);
            return ResponseEntity.ok(adminRepository.save(admin));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Assign Tickets to Agents
    @PostMapping("/assignTicket")
    public ResponseEntity<Ticket> assignTicket(@RequestParam Long ticketId, @RequestParam Long agentId) {
        Optional<Ticket> ticketOpt = ticketRepository.findById(ticketId);
        Optional<Admin> adminOpt = adminRepository.findById(agentId); // Assuming agentId is an admin ID for now
        return ticketOpt.flatMap(ticket -> adminOpt.map(admin -> {
            ticket.setAssignedAgentId(agentId); // Assign to agent
            ticket.setAdmin(admin); // Optional: Track admin oversight
            return ResponseEntity.ok(ticketRepository.save(ticket));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update Customer DB (Placeholder for integration with CustomerController)
    @PostMapping("/updateCustomerDB")
    public ResponseEntity<String> updateCustomerDB(@RequestParam Long adminId) {
        Optional<Admin> adminOpt = adminRepository.findById(adminId);
        return adminOpt.map(admin -> {
            // Logic to update customer database, potentially calling CustomerController
            return ResponseEntity.ok("Customer DB updated by Admin ID: " + adminId);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get all admins
    @GetMapping("/all")
    public ResponseEntity<List<Admin>> getAllAdmins() {
        return ResponseEntity.ok(adminRepository.findAll());
    }
}