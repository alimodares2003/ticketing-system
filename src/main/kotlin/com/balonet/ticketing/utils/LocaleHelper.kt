package com.balonet.ticketing.utils

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class LocaleHelper {
    @Value("\${application.language}")
    private val language: String? = null

    @Value("\${application.country}")
    private val country: String? = null

    fun getString(key: String): String {
        val locale = Locale(language, country)
        val messages = ResourceBundle.getBundle("messages", locale)
        return messages.getString(key)
    }

    fun getString(key: String, language: String?): String {
        val locale = Locale(language)
        val messages = ResourceBundle.getBundle("messages", locale)
        return messages.getString(key)
    }
}