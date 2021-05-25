package com.balonet.ticketing.service

import com.balonet.ticketing.model.request.TicketCreateRequest
import com.balonet.ticketing.model.request.TicketUpdateRequest
import com.balonet.ticketing.model.response.TicketListResponse
import com.balonet.ticketing.model.response.TicketResponse

interface TicketService {
    fun listAll(): List<TicketListResponse>
    fun get(): List<TicketResponse>
    fun create(ticketCreateRequest: TicketCreateRequest): TicketResponse
    fun partialUpdate(id: Long, ticketUpdateRequest: TicketUpdateRequest): TicketResponse
}