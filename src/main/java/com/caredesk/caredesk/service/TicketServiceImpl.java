package com.caredesk.caredesk.service;

import com.caredesk.caredesk.model.Ticket;
import com.caredesk.caredesk.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public List<Ticket> findByCustomerId(Long customerId) {
        return ticketRepository.findByCustomerId(customerId);
    }

    @Override
    public Ticket findById(Long id) {
        return ticketRepository.findById(id).orElse(null);
    }

    @Override
    public Ticket save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public void deleteById(Long id) {
        ticketRepository.deleteById(id);
    }

    // --- THIS IS THE MISSING METHOD ---
    // You must add this method to match the TicketService interface
    @Override
    public List<Ticket> findUnassignedTickets() {
        return ticketRepository.findByAssignedAgentIsNull();
    }
}