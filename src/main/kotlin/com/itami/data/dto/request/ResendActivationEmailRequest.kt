package com.itami.data.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class ResendActivationEmailRequest(
    val email: String,
)
