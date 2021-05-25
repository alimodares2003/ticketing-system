package com.balonet.ticketing.config

import com.balonet.ticketing.service.MyUserDetailsService
import com.balonet.ticketing.utils.JwtHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthorizationFilter : OncePerRequestFilter() {

    @Autowired
    private lateinit var jwtHelper: JwtHelper

    @Autowired
    private lateinit var userDetailsService: MyUserDetailsService

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // look for Bearer auth header
        val header = request.getHeader(HttpHeaders.AUTHORIZATION)
        println(header)
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }
        val token = header.substring(7)
        val userModel = jwtHelper.validateTokenAndGetUserModel(token)

        // set user details on spring security context
        val userDetails: UserDetails = userDetailsService.loadUserByUsername(userModel?.email.orEmpty())
        val authentication = UsernamePasswordAuthenticationToken(
            userDetails, userModel, userDetails.authorities
        )
        authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
        SecurityContextHolder.getContext().authentication = authentication

        // continue with authenticated user
        filterChain.doFilter(request, response)
    }

}