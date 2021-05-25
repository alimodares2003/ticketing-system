package com.balonet.ticketing.model.request

import com.balonet.ticketing.entity.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class RegisterRequest(
    @field:NotNull
    @field:Email
    @field:Size(max = 255)
    var email: String,
    @field:NotBlank
    @field:Size(min = 4 ,max = 255)
    var password: String,
    @field:NotBlank
    @field:Size(max = 255)
    var firstname: String,
    @field:NotBlank
    @field:Size(max = 255)
    var lastname: String
) {
    fun mapToEntity(): User {
        val user = User()
        user.email = this.email
        user.password = BCryptPasswordEncoder().encode(this.password)
        user.firstname = this.firstname
        user.lastname = this.lastname
        return user
    }
}
