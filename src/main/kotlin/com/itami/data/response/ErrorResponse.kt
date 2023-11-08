package com.itami.data.response

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val errorMessage: String?
)
