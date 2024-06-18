package com.itami.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.ratelimit.*
import kotlin.time.Duration.Companion.seconds


val DEFAULT_RATE_LIMIT = RateLimitName("app-rate-limit")
val AUTH_RATE_LIMIT = RateLimitName("auth-rate-limit")
val FEEDBACK_RATE_LIMIT = RateLimitName("feedback-rate-limit")

fun Application.configureRateLimit() {
    install(RateLimit) {
        register(AUTH_RATE_LIMIT) {
            rateLimiter(limit = 5, refillPeriod = 60.seconds)
            requestKey { it.request.origin.remoteHost }
        }
        register(DEFAULT_RATE_LIMIT) {
            rateLimiter(limit = 60, refillPeriod = 60.seconds)
            requestKey { it.request.origin.remoteHost }
        }
        register(FEEDBACK_RATE_LIMIT) {
            rateLimiter(2, refillPeriod = 60.seconds)
            requestKey { it.request.origin.remoteHost }
        }
    }
}