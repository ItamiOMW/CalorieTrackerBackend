package com.itami.data.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class RemoveConsumedWaterRequest(
    val datetime: String,
    val waterMlToRemove: Int,
)
