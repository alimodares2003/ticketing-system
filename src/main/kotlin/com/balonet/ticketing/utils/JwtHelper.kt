package com.balonet.ticketing.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.exceptions.TokenExpiredException
import com.balonet.ticketing.entity.User
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtHelper {
    private var hmac512: Algorithm? = null
    private var verifier: JWTVerifier? = null

    @Value("\${jwt.expiration}")
    private val expirationTime: Long = 0L

    init {
        hmac512 = Algorithm.HMAC512("secretKey")
        verifier = JWT.require(hmac512).build()
    }

    fun generateToken(userDetails: UserDetails, id: Long?): String? {
        return JWT.create()
            .withSubject(userDetails.username)
            .withClaim("id", id)
            .withExpiresAt(Date(System.currentTimeMillis() + expirationTime))
            .sign(hmac512)
    }

    fun validateTokenAndGetUserModel(token: String?): User? {
        return try {
            val decodedToken = verifier?.verify(token)
            val userModel = User()
            userModel.email = decodedToken?.subject
            userModel.id = decodedToken?.claims?.get("id").toString().toLong()
            return userModel
        } catch (e: TokenExpiredException) {
            println("token invalid2: " + e.message)
            null
        } catch (verificationEx: JWTVerificationException) {
            println("token invalid: " + verificationEx.message)
            null
        }
    }
}