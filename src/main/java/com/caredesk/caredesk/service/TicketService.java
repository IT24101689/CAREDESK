package com.caredesk.caredesk.service;

import com.caredesk.caredesk.model.Ticket;
import java.util.List;

// Make sure this is declared as an 'interface', not a 'class'
public interface TicketService {

    List<Ticket> findByCustomerId(Long customerId);

    Ticket findById(Long id);

    Ticket save(Ticket ticket);

    void deleteById(Long id);
}