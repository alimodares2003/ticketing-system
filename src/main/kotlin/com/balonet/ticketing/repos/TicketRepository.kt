package com.balonet.ticketing.repos

import com.balonet.ticketing.entity.Ticket
import org.springframework.data.jpa.repository.JpaRepository


interface TicketRepository : JpaRepository<Ticket, Long> {
    fun findAllByUserId(id: Long): List<Ticket>
}
