package com.balonet.ticketing.model.request

import com.balonet.ticketing.entity.User
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class UserUpdateRequest(
    @field:NotBlank
    @field:Size(max = 255)
    var firstname: String,
    @field:NotBlank
    @field:Size(max = 255)
    var lastname: String,
    @field:NotBlank
    @field:Size(max = 255)
    var role: String,
) {
    fun mapToEntity(user: User): User {
        user.firstname = this.firstname
        user.lastname = this.lastname
        user.role = this.role
        return user
    }
}