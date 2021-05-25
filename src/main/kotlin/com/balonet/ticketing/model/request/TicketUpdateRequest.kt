package com.balonet.ticketing.model.request

import javax.validation.constraints.NotNull

data class TicketUpdateRequest(
    @field:NotNull
    var status: Int = 0
)