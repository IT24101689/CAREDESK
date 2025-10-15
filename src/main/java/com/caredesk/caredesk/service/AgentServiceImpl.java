package com.caredesk.caredesk.service;

import com.caredesk.caredesk.model.Agent;
import com.caredesk.caredesk.model.Ticket;
import com.caredesk.caredesk.repository.AgentRepository;
import com.caredesk.caredesk.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Service
public class AgentServiceImpl implements AgentService {

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // This interface is assumed to exist from previous steps
    // If not, create an AgentService interface with these method signatures.

    public List<Agent> findAllAgents() {
        return agentRepository.findAll();
    }

    @Override
    public Agent saveAgent(Agent agent) {
        // Handle password encoding logic
        if (agent.getPassword() != null && !agent.getPassword().isEmpty()) {
            // If a new password is provided, encode it
            agent.setPassword(passwordEncoder.encode(agent.getPassword()));
        } else {
            // If updating an agent and password is blank, keep the old password
            if (agent.getId() != null) {
                Agent existingAgent = agentRepository.findById(agent.getId()).orElse(null);
                if (existingAgent != null) {
                    agent.setPassword(existingAgent.getPassword());
                }
            }
        }
        return agentRepository.save(agent);
    }

    public Agent findAgentById(Long id) {
        return agentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agent not found with id: " + id));
    }

    public void deleteAgentById(Long id) {
        Agent agent = findAgentById(id);

        // Before deleting an agent, unassign their tickets.
        for (Ticket ticket : agent.getAssignedTickets()) {
            ticket.setAssignedAgent(null); // <-- CORRECTED METHOD NAME
            ticketRepository.save(ticket);
        }

        agentRepository.deleteById(id);
    }

    public void assignTicketToAgent(Long ticketId, Long agentId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new EntityNotFoundException("Ticket not found"));
        Agent agent = findAgentById(agentId);

        ticket.setAssignedAgent(agent); // <-- CORRECTED METHOD NAME
        ticketRepository.save(ticket);
    }

    public void unassignTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new EntityNotFoundException("Ticket not found"));

        ticket.setAssignedAgent(null); // <-- CORRECTED METHOD NAME
        ticketRepository.save(ticket);
    }
}


