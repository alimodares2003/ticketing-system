package com.balonet.ticketing.model.response

import java.sql.Timestamp

data class TicketResponse(
    var id: Long? = null,
    var title: String? = null,
    var description: String? = null,
    var status: String? = null,
    var cdt: Timestamp? = null,
    var udt: Timestamp? = null,
)