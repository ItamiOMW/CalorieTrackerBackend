package com.itami.data.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateMealRequest(
    val name: String,
    val consumedFoods: List<ConsumedFoodRequest>,
    val datetime: String,
)