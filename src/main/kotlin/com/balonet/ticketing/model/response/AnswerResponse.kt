package com.balonet.ticketing.model.response

import com.balonet.ticketing.entity.Answer
import java.sql.Timestamp

data class AnswerResponse(
    var id: Long? = null,
    var description: String? = null,
    var operator: Boolean? = null,
    var cdt: Timestamp? = null,
) {
    companion object {
        fun mapToModel(answer: Answer): AnswerResponse {
            return AnswerResponse(
                answer.id,
                answer.description,
                answer.operator,
                answer.cdt,
            )
        }
    }
}