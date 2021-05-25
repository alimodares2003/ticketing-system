package com.balonet.ticketing.service.impl

import com.balonet.ticketing.entity.Answer
import com.balonet.ticketing.model.Role
import com.balonet.ticketing.model.TicketStatus
import com.balonet.ticketing.model.request.AnswerCreateRequest
import com.balonet.ticketing.model.response.AnswerResponse
import com.balonet.ticketing.repos.AnswerRepository
import com.balonet.ticketing.repos.TicketRepository
import com.balonet.ticketing.repos.UserRepository
import com.balonet.ticketing.service.AnswerService
import com.balonet.ticketing.utils.LocaleHelper
import com.balonet.ticketing.utils.getUserModel
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class AnswerServiceImpl(
    private val answerRepository: AnswerRepository,
    private val ticketRepository: TicketRepository,
    private val userRepository: UserRepository,
    private val localeHelper: LocaleHelper
) : AnswerService {

    override fun listTicketAnswers(id: Long): List<AnswerResponse> {
        val currentUser = userRepository.findById(getUserModel().id!!)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }

        val ticket = ticketRepository.findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }

        if (currentUser.role == Role.USER.name && ticket.user!!.id != currentUser.id)
            throw ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                localeHelper.getString("accessDenied")
            )

        val userTicketsAnswers = answerRepository.findAllByTicketUserIdAndTicketId(ticket.user!!.id!!, id)
        return userTicketsAnswers.map {
            AnswerResponse(it.id, it.description, it.operator, it.cdt)
        }
    }

    override fun create(ticketId: Long, answerCreateRequest: AnswerCreateRequest): Answer {
        val ticket = ticketRepository.findById(ticketId)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }

        if (ticket.status == TicketStatus.CLOSE.ordinal)
            throw ResponseStatusException(HttpStatus.OK, localeHelper.getString("ticketClosed"))

        val currentUser = userRepository.findById(getUserModel().id!!)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }

        val answer = Answer()

        if (currentUser.role == Role.USER.name) {
            if (ticket.user!!.id != currentUser.id)
                throw ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    localeHelper.getString("accessDenied")
                )

            answer.operator = false
            answer.description = answerCreateRequest.description
            answer.ticket = ticket
            ticket.status = TicketStatus.CUSTOMER_RESPONSE.ordinal
            ticketRepository.save(ticket)
            return answerRepository.save(answer)
        }

        if (currentUser.role == Role.ADMIN.name || currentUser.role == Role.SUPPORT.name) {
            answer.operator = true
            answer.description = answerCreateRequest.description
            answer.ticket = ticket
            ticket.status = TicketStatus.ANSWERED.ordinal
            ticketRepository.save(ticket)
            return answerRepository.save(answer)
        }

        return answer
    }

}