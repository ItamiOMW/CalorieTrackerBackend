package com.itami.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.ratelimit.*
import kotlin.time.Duration.Companion.seconds


val AUTH_RATE_LIMIT = RateLimitName("auth-rate-limit")
val APP_RATE_LIMIT = RateLimitName("app-rate-limit")

fun Application.configureRateLimit() {
    install(RateLimit) {
        register(AUTH_RATE_LIMIT) {
            rateLimiter(limit = 5, refillPeriod = 60.seconds)
            requestKey { it.request.origin.remoteHost }
        }
        register(APP_RATE_LIMIT) {
            rateLimiter(limit = 60, refillPeriod = 60.seconds)
            requestKey { it.request.origin.remoteHost }
        }
    }
}