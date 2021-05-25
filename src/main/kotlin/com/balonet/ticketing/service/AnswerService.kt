package com.balonet.ticketing.service

import com.balonet.ticketing.entity.Answer
import com.balonet.ticketing.model.request.AnswerCreateRequest
import com.balonet.ticketing.model.response.AnswerResponse

interface AnswerService {
    fun listTicketAnswers(id: Long): List<AnswerResponse>
    fun create(ticketId: Long, answerCreateRequest: AnswerCreateRequest): Answer
}