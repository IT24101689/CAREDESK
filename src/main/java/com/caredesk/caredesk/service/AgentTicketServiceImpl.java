package com.caredesk.caredesk.service;

import com.caredesk.caredesk.model.Agent;
import com.caredesk.caredesk.model.Ticket;
import com.caredesk.caredesk.model.TicketResponse;
import com.caredesk.caredesk.repository.TicketRepository;
import com.caredesk.caredesk.repository.TicketResponseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AgentTicketServiceImpl implements AgentTicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketResponseRepository responseRepository;

    @Override
    public List<Ticket> findTicketsByAgent(Agent agent) {
        // Assuming your TicketRepository has a method to find by assignedAgent
        return ticketRepository.findByAssignedAgent(agent);
    }

    @Override
    public Optional<Ticket> findTicketById(Long ticketId) {
        return ticketRepository.findById(ticketId);
    }

    @Override
    public Ticket updateTicketStatus(Long ticketId, String status) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new EntityNotFoundException("Ticket not found with id: " + ticketId));
        ticket.setStatus(status);
        return ticketRepository.save(ticket);
    }

    @Override
    public TicketResponse addResponseToTicket(String content, Long ticketId, Agent agent) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new EntityNotFoundException("Ticket not found with id: " + ticketId));

        TicketResponse response = new TicketResponse();
        response.setContent(content);
        response.setTicket(ticket);
        response.setAgent(agent);
        response.setCreatedAt(LocalDateTime.now());

        return responseRepository.save(response);
    }
}