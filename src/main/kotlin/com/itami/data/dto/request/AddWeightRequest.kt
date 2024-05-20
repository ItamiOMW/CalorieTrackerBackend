package com.itami.data.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class AddWeightRequest(
    val encodedDatetime: String,
    val weightGrams: Int,
)
