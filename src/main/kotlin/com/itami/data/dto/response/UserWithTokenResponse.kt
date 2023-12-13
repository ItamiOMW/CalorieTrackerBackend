package com.itami.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class UserWithTokenResponse(
    val user: UserResponse,
    val token: String
)
