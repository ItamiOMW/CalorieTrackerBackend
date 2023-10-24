package com.itami.utils

import java.time.Clock
import java.time.LocalDateTime
import java.time.ZoneOffset

fun currentDateTime(): LocalDateTime {
    return LocalDateTime.now(Clock.systemUTC())
}

fun LocalDateTime.toLong(): Long {
    return this.toEpochSecond(ZoneOffset.UTC)
}