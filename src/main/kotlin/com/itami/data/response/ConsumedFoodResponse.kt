package com.itami.data.response

import kotlinx.serialization.Serializable

@Serializable
data class ConsumedFoodResponse(
    val id: Int,
    val food: FoodResponse,
    val grams: Int,
)