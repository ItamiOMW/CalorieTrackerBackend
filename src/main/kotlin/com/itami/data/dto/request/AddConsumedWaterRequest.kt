package com.itami.data.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class AddConsumedWaterRequest(
    val datetime: String,
    val waterMl: Int,
)
