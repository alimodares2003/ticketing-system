package com.balonet.ticketing.model.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class LoginRequest(
    @field:NotNull
    @field:Email
    @field:Size(max = 255)
    var email: String,
    @field:NotBlank
    @field:Size(max = 255)
    var password: String
)