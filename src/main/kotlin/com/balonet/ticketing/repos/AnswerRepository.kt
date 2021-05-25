package com.balonet.ticketing.repos

import com.balonet.ticketing.entity.Answer
import org.springframework.data.jpa.repository.JpaRepository


interface AnswerRepository : JpaRepository<Answer, Long> {
    fun findAllByTicketUserIdAndTicketId(userId: Long, ticketId: Long): List<Answer>
}