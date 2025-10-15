package com.caredesk.caredesk.repository;

import com.caredesk.caredesk.model.TicketResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketResponseRepository extends JpaRepository<TicketResponse, Long> {
    // Spring Data JPA will provide all necessary CRUD methods
}