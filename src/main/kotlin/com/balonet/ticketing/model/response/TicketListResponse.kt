package com.balonet.ticketing.model.response

import java.sql.Timestamp

data class TicketListResponse(
    var id: Long? = null,
    var user: Long? = null,
    var title: String? = null,
    var description: String? = null,
    var status: String? = null,
    var cdt: Timestamp? = null,
    var udt: Timestamp? = null,
)