package com.itami.data.model.user

import java.time.LocalDateTime

data class PasswordResetCode(
    val code: Int,
    val email: String,
    val expiresAt: LocalDateTime,
)
