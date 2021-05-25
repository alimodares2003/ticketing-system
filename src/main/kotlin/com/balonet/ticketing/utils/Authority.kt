package com.balonet.ticketing.utils

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Authority(
    vararg val value: String,
    val allInRole: Boolean = false
)