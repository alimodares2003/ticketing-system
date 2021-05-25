package com.balonet.ticketing.rest

import com.balonet.ticketing.model.request.LoginRequest
import com.balonet.ticketing.model.request.RegisterRequest
import com.balonet.ticketing.model.request.UserPartialUpdateRequest
import com.balonet.ticketing.model.request.UserUpdateRequest
import com.balonet.ticketing.model.response.LoginResponse
import com.balonet.ticketing.model.response.ProfileResponse
import com.balonet.ticketing.model.response.RegisterResponse
import com.balonet.ticketing.service.UserService
import com.balonet.ticketing.utils.Authority
import com.balonet.ticketing.utils.decodeAsJWT
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@RestController
@RequestMapping(
    value = ["/api/users"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
class UserController(
    private val userService: UserService
) {

    @PostMapping("/login")
    fun login(@RequestBody @Valid loginRequest: LoginRequest): ResponseEntity<LoginResponse> =
        ResponseEntity.ok(LoginResponse(userService.authenticate(loginRequest)!!))


    @PostMapping("/register")
    fun register(@RequestBody @Valid registerRequest: RegisterRequest): ResponseEntity<RegisterResponse> =
        ResponseEntity<RegisterResponse>(
            RegisterResponse.mapToModel(userService.create(registerRequest.mapToEntity())),
            HttpStatus.CREATED
        )

    @GetMapping("/me")
    fun profile(@RequestHeader("Authorization") authorization: String): ResponseEntity<ProfileResponse> {
        val email = authorization.decodeAsJWT().subject
        return ResponseEntity.ok(ProfileResponse.mapToModel(userService.getWithEmail(email)))
    }

    @GetMapping
    @Authority("ADMIN")
    fun getAllUsers(pageable: Pageable): ResponseEntity<Page<ProfileResponse>> {
        return ResponseEntity.ok(userService.listAll(pageable))
    }

    @PutMapping("/{id}")
    @Authority("ADMIN")
    fun updateUser(@PathVariable id: Long, @RequestBody @Valid updateRequest: UserUpdateRequest):
            ResponseEntity<ProfileResponse> {
        return ResponseEntity.ok(ProfileResponse.mapToModel(userService.update(id, updateRequest)))
    }

    @PatchMapping("/{id}")
    @Authority("ADMIN")
    fun partialUpdateUser(
        @PathVariable id: Long,
        @RequestBody @Valid partialUpdateRequest: UserPartialUpdateRequest
    ): ResponseEntity<ProfileResponse> {
        return ResponseEntity.ok(
            ProfileResponse.mapToModel(userService.partialUpdate(id, partialUpdateRequest))
        )
    }

    @DeleteMapping("/{id}")
    @Authority("ADMIN")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Void> {
        userService.delete(id)
        return ResponseEntity.noContent().build()
    }

}
