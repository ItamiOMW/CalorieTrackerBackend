package com.itami.data.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class ConsumedFoodRequest(
    val foodId: Int,
    val grams: Int,
)