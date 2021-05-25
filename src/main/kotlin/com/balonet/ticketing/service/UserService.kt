package com.balonet.ticketing.service

import com.balonet.ticketing.entity.User
import com.balonet.ticketing.model.request.LoginRequest
import com.balonet.ticketing.model.request.UserPartialUpdateRequest
import com.balonet.ticketing.model.request.UserUpdateRequest
import com.balonet.ticketing.model.response.ProfileResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface UserService {
    fun listAll(pageable: Pageable): Page<ProfileResponse>
    fun getWithEmail(email: String): User
    fun create(user: User): User
    fun update(id: Long, updateRequest: UserUpdateRequest): User
    fun partialUpdate(id: Long, partialUpdateRequest: UserPartialUpdateRequest): User
    fun delete(id: Long)
    fun authenticate(loginRequest: LoginRequest): String?
}