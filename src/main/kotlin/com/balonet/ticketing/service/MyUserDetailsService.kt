package com.balonet.ticketing.service

import com.balonet.ticketing.entity.User
import com.balonet.ticketing.repos.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class MyUserDetailsService : UserDetailsService {

    @Autowired
    private lateinit var userRepository: UserRepository

    override fun loadUserByUsername(email: String): UserDetails {
        val user: User =
            userRepository.findUserByEmail(email).orElseThrow { UsernameNotFoundException("User $email not found") }
        return org.springframework.security.core.userdetails.User(
            email,
            user.password,
            listOf(SimpleGrantedAuthority(user.role))
        )
    }
}