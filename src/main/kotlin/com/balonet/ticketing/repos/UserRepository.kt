package com.balonet.ticketing.repos

import com.balonet.ticketing.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*


interface UserRepository : JpaRepository<User, Long> {
    fun findUserByEmail(email: String) : Optional<User>
}
