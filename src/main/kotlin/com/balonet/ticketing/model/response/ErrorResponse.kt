package com.balonet.ticketing.model.response

import com.balonet.ticketing.model.FieldError


data class ErrorResponse(
    var httpStatus: Int? = null,
    var exception: String? = null,
    var message: String? = null,
    var fieldErrors: List<FieldError>? = null
)
