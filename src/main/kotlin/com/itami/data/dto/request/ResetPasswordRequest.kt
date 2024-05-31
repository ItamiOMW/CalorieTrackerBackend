package com.itami.data.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class ResetPasswordRequest(
    val code: Int,
    val email: String,
    val newPassword: String,
)
