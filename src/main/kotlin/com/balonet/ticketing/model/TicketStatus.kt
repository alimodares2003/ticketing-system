package com.balonet.ticketing.model

enum class TicketStatus(var key: String) {
    OPEN("open"),
    CLOSE("close"),
    IN_PROGRESS("inProgress"),
    ANSWERED("answered"),
    CUSTOMER_RESPONSE("customerResponse");

    companion object {
        private val VALUES = values()
        fun getByValue(value: Int) = VALUES.firstOrNull { it.ordinal == value }
    }
}