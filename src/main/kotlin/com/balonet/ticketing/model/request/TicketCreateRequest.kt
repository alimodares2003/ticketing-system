package com.balonet.ticketing.model.request

import com.balonet.ticketing.entity.Ticket
import com.balonet.ticketing.model.TicketStatus
import com.balonet.ticketing.utils.getUserModel
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class TicketCreateRequest(
    @field:NotBlank
    @field:Size(max = 255)
    var title: String,
    @field:NotBlank
    var description: String
) {
    fun mapToEntity(): Ticket {
        val ticket = Ticket()
        ticket.title = this.title
        ticket.description = this.description
        ticket.status = TicketStatus.OPEN.ordinal
        ticket.user = getUserModel()
        return ticket
    }
}