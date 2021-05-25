package com.balonet.ticketing.model.request

import com.fasterxml.jackson.annotation.JsonCreator
import javax.validation.constraints.NotBlank

data class AnswerCreateRequest @JsonCreator constructor(
    @field:NotBlank
    var description: String
)