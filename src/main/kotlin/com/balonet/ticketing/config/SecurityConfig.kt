package com.balonet.ticketing.config

import com.balonet.ticketing.entity.User
import com.balonet.ticketing.model.Role
import com.balonet.ticketing.repos.UserRepository
import com.balonet.ticketing.service.MyUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val userDetailsService: MyUserDetailsService,
    private val authorizationFilter: AuthorizationFilter
) : WebSecurityConfigurerAdapter() {

    @Value("\${application.superuser.username}")
    private val username: String? = null

    @Value("\${application.superuser.password}")
    private val password: String? = null

    private val AUTH_WHITELIST = arrayOf(
        "/api/users/login",
        "/api/users/register",

        "/h2-console/**",
        "/v2/api-docs",
        "/swagger-resources",
        "/swagger-resources/**",
        "/configuration/ui",
        "/configuration/security",
        "/swagger-ui.html",
        "/webjars/**",
        "/v3/api-docs/**",
        "/swagger-ui/**"
    )

    @Autowired
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService<UserDetailsService?>(userDetailsService).passwordEncoder(passwordEncoder())
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder? {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun createSuperUser(userRepository: UserRepository) = ApplicationRunner {
        val user = User()
        user.email = username
        user.password = BCryptPasswordEncoder().encode(password)
        user.role = Role.ADMIN.name
        if (!userRepository.findUserByEmail(user.email!!).isPresent)
            userRepository.save(user)
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager? {
        return super.authenticationManagerBean()
    }

    override fun configure(http: HttpSecurity) {
        http.cors().and()
            .csrf().disable()
            .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .authorizeRequests()
            .antMatchers(*AUTH_WHITELIST).permitAll()
            .anyRequest().authenticated()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        http.headers().frameOptions().disable()
    }

}