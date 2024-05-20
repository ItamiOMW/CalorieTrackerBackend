package com.itami.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class WeightResponse(
    val id: Int,
    val weightGrams: Int,
    val datetime: String,
)
