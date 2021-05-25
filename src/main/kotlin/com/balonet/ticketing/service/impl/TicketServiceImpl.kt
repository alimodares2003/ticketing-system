package com.balonet.ticketing.service.impl

import com.balonet.ticketing.entity.Ticket
import com.balonet.ticketing.model.TicketStatus
import com.balonet.ticketing.model.request.TicketCreateRequest
import com.balonet.ticketing.model.request.TicketUpdateRequest
import com.balonet.ticketing.model.response.TicketListResponse
import com.balonet.ticketing.model.response.TicketResponse
import com.balonet.ticketing.repos.TicketRepository
import com.balonet.ticketing.service.TicketService
import com.balonet.ticketing.utils.LocaleHelper
import com.balonet.ticketing.utils.getUserModel
import com.balonet.ticketing.utils.serializeToMap
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.util.ReflectionUtils
import org.springframework.web.server.ResponseStatusException


@Service
class TicketServiceImpl(
    private val ticketRepository: TicketRepository,
    private val localeHelper: LocaleHelper
) : TicketService {

    override fun listAll(): List<TicketListResponse> {
        return ticketRepository.findAll().map {
            TicketListResponse(
                it.id,
                it.user?.id,
                it.title,
                it.description,
                localeHelper.getString(TicketStatus.getByValue(it.status)!!.key),
                it.cdt,
                it.udt
            )
        }
    }

    override fun get(): List<TicketResponse> {
        val tickets = ticketRepository.findAllByUserId(getUserModel().id!!)
        return tickets.map { mapToModel(it) }
    }

    override fun create(ticketCreateRequest: TicketCreateRequest): TicketResponse {
        val ticket = ticketRepository.save(ticketCreateRequest.mapToEntity())
        return mapToModel(ticket)
    }

    override fun partialUpdate(id: Long, ticketUpdateRequest: TicketUpdateRequest): TicketResponse {
        val ticket = ticketRepository.findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }

        ticketUpdateRequest.serializeToMap().forEach { (k, v) ->
            val field = ReflectionUtils.findField(Ticket::class.java, k)
            if (field != null) {
                field.isAccessible = true
                if (v is Double)
                    ReflectionUtils.setField(field, ticket, v.toInt())
                else
                    ReflectionUtils.setField(field, ticket, v)
            }
        }
        return mapToModel(ticketRepository.save(ticket))
    }

    fun mapToModel(ticket: Ticket): TicketResponse {
        return TicketResponse(
            ticket.id,
            ticket.title,
            ticket.description,
            localeHelper.getString(TicketStatus.getByValue(ticket.status)!!.key),
            ticket.cdt,
            ticket.udt
        )
    }
}
