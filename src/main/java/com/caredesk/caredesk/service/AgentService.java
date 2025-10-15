package com.caredesk.caredesk.service;

import com.caredesk.caredesk.model.Agent;
import java.util.List;

public interface AgentService {
    List<Agent> findAllAgents();
    Agent saveAgent(Agent agent);
    Agent findAgentById(Long id); // The method is defined here
    void deleteAgentById(Long id);
    void assignTicketToAgent(Long ticketId, Long agentId);
    void unassignTicket(Long ticketId);
}