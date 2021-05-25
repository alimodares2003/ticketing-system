package com.balonet.ticketing.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.interfaces.DecodedJWT
import com.balonet.ticketing.entity.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.springframework.security.core.context.SecurityContextHolder

val gson = Gson()

//convert a data class to a map
fun <T> T.serializeToMap(): Map<String, Any> {
    return convert()
}

inline fun <I, reified O> I.convert(): O {
    val json = gson.toJson(this)
    return gson.fromJson(json, object : TypeToken<O>() {}.type)
}

//decode jwt access token
fun String.decodeAsJWT(): DecodedJWT {
    return JWT.decode(this.substring(7))
}

fun getUserModel(): User {
    return SecurityContextHolder.getContext().authentication.credentials as User
}