package com.balonet.ticketing.utils

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Aspect
@Component
class AuthorityValidator {
    @Autowired
    lateinit var localeHelper: LocaleHelper

    @Around("@annotation(Authority)")
    fun validate(pjp: ProceedingJoinPoint): Any {
        val signature = pjp.signature as MethodSignature
        val method = signature.method
        val validateAction: Authority = method.getAnnotation(Authority::class.java)
        val roles: Array<out String> = validateAction.value
        val authorities = ArrayList<String>()
        SecurityContextHolder.getContext().authentication.authorities
            .forEach { g: GrantedAuthority? -> authorities.add(g!!.authority) }
        if (validateAction.allInRole) {
            roles.forEach {
                if (it !in authorities) throw ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    localeHelper.getString("accessDenied")
                )
            }
        } else {
            if (!roles.any(authorities::contains)) throw ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                localeHelper.getString("accessDenied")
            )
        }

        return pjp.proceed()
    }
}