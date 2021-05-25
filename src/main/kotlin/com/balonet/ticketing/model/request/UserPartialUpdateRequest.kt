package com.balonet.ticketing.model.request

import javax.validation.constraints.Size

data class UserPartialUpdateRequest(
    @field:Size(max = 255)
    var firstname: String? = null,
    @field:Size(max = 255)
    var lastname: String? = null,
    @field:Size(max = 255)
    var role: String? = null,
)