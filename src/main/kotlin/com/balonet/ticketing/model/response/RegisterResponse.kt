package com.balonet.ticketing.model.response

import com.balonet.ticketing.entity.User
import java.sql.Timestamp

data class RegisterResponse(
    var email: String? = null,
    var firstname: String? = null,
    var lastname: String? = null,
    var role: String? = null,
    var cdt: Timestamp? = null,
    var udt: Timestamp? = null,
) {
    companion object {
        fun mapToModel(user: User): RegisterResponse {
            return RegisterResponse(
                user.email,
                user.firstname,
                user.lastname,
                user.role,
                user.cdt,
                user.udt
            )
        }
    }
}