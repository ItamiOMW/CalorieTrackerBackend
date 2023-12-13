package com.itami.data.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class UpdateMealRequest(
    val name: String,
    val consumedFoods: List<ConsumedFoodRequest>,
)