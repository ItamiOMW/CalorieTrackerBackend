package com.itami.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val errorMessage: String?
)
