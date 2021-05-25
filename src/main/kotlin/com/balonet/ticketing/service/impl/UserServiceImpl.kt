package com.balonet.ticketing.service.impl

import com.balonet.ticketing.entity.User
import com.balonet.ticketing.model.request.LoginRequest
import com.balonet.ticketing.model.request.UserPartialUpdateRequest
import com.balonet.ticketing.model.request.UserUpdateRequest
import com.balonet.ticketing.model.response.ProfileResponse
import com.balonet.ticketing.repos.UserRepository
import com.balonet.ticketing.service.MyUserDetailsService
import com.balonet.ticketing.service.UserService
import com.balonet.ticketing.utils.JwtHelper
import com.balonet.ticketing.utils.LocaleHelper
import com.balonet.ticketing.utils.serializeToMap
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import org.springframework.util.ReflectionUtils
import org.springframework.web.server.ResponseStatusException


@Service
class UserServiceImpl(
    private val jwtHelper: JwtHelper,
    private val userDetailsService: MyUserDetailsService,
    private val authenticationManager: AuthenticationManager,
    private val userRepository: UserRepository,
    private val localeHelper: LocaleHelper
) : UserService {

    override fun listAll(pageable: Pageable): Page<ProfileResponse> {
        return userRepository.findAll(pageable).map { ProfileResponse.mapToModel(it) }
    }

    override fun getWithEmail(email: String): User {
        return userRepository.findUserByEmail(email)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }
    }

    override fun create(user: User): User {
        return try {
            userRepository.save(user)
        } catch (ex: DataIntegrityViolationException) {
            throw ResponseStatusException(HttpStatus.CONFLICT, localeHelper.getString("duplicateUser"))
        }
    }

    override fun update(id: Long, updateRequest: UserUpdateRequest): User {
        val user: User = userRepository.findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }
        return userRepository.save(updateRequest.mapToEntity(user))
    }

    override fun partialUpdate(id: Long, partialUpdateRequest: UserPartialUpdateRequest): User {
        val user = userRepository.findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }

        partialUpdateRequest.serializeToMap().forEach { (k, v) ->
            val field = ReflectionUtils.findField(User::class.java, k)
            if (field != null) {
                field.isAccessible = true
                ReflectionUtils.setField(field, user, v)
            }
        }
        return userRepository.save(user)
    }

    override fun delete(id: Long) {
        userRepository.deleteById(id)
    }

    override fun authenticate(loginRequest: LoginRequest): String? {
        try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    loginRequest.email, loginRequest.password
                )
            )
        } catch (ex: BadCredentialsException) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, localeHelper.getString("invalidLogin"))
        }
        val id: Long? = userRepository.findUserByEmail(loginRequest.email).orElseThrow().id
        val userDetails: UserDetails = userDetailsService.loadUserByUsername(loginRequest.email)
        return jwtHelper.generateToken(userDetails, id)
    }

}
