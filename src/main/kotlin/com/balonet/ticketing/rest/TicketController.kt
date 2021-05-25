package com.balonet.ticketing.rest

import com.balonet.ticketing.model.request.TicketCreateRequest
import com.balonet.ticketing.model.request.TicketUpdateRequest
import com.balonet.ticketing.model.response.TicketListResponse
import com.balonet.ticketing.model.response.TicketResponse
import com.balonet.ticketing.service.TicketService
import com.balonet.ticketing.utils.Authority
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@RestController
@RequestMapping(
    value = ["/api/tickets"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
class TicketController(
    private val ticketService: TicketService
) {

    @GetMapping
    @Authority("ADMIN", "SUPPORT")
    fun getAllTickets(): ResponseEntity<List<TicketListResponse>> =
        ResponseEntity.ok(ticketService.listAll())

    @GetMapping("/me")
    fun getTicket(): ResponseEntity<List<TicketResponse>> =
        ResponseEntity.ok(ticketService.get())

    @PostMapping
    fun createTicket(@RequestBody @Valid ticketCreateRequest: TicketCreateRequest): ResponseEntity<TicketResponse> =
        ResponseEntity(ticketService.create(ticketCreateRequest), HttpStatus.CREATED)

    @PatchMapping("/{id}")
    @Authority("ADMIN", "SUPPORT")
    fun updateTicket(
        @RequestBody @Valid ticketUpdateRequest: TicketUpdateRequest,
        @PathVariable id: Long
    ): ResponseEntity<TicketResponse> =
        ResponseEntity.ok(ticketService.partialUpdate(id, ticketUpdateRequest))

}
