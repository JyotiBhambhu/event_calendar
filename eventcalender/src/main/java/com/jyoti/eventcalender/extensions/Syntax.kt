package com.jyoti.eventcalender.extensions

val <T> T.exhaustive: T
    get() = this

fun Int?.orZero(): Int = this ?: 0

fun Long?.orZero(): Long = this ?: 0L

fun Double?.orZero(): Double = this ?: 0.0

fun Boolean?.orFalse(): Boolean = this ?: false

fun Float?.orZero(): Float = this ?: 0f
