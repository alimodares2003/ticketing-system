package com.balonet.ticketing.rest

import com.balonet.ticketing.model.request.AnswerCreateRequest
import com.balonet.ticketing.model.response.AnswerResponse
import com.balonet.ticketing.service.AnswerService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@RestController
@RequestMapping(
    value = ["/api/answers"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
class AnswerController(
    private val answerService: AnswerService
) {
    @GetMapping("/{ticketId}")
    fun getTicketAnswers(@PathVariable ticketId: Long): ResponseEntity<List<AnswerResponse>> =
        ResponseEntity.ok(answerService.listTicketAnswers(ticketId))

    @PostMapping("/{ticketId}")
    fun createAnswer(
        @RequestBody @Valid answerCreateRequest: AnswerCreateRequest,
        @PathVariable ticketId: Long
    ): ResponseEntity<AnswerResponse> =
        ResponseEntity(
            AnswerResponse.mapToModel(answerService.create(ticketId, answerCreateRequest)),
            HttpStatus.CREATED
        )
}
 