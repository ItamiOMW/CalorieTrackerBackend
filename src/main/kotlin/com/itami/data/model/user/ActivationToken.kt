package com.itami.data.model.user

import java.time.LocalDateTime

data class ActivationToken(
    val token: String,
    val email: String,
    val expiresAt: LocalDateTime,
)
