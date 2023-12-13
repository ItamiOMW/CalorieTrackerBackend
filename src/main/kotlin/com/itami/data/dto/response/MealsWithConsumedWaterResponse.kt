package com.itami.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class MealsWithConsumedWaterResponse(
    val meals: List<MealResponse>,
    val consumedWater: ConsumedWaterResponse? = null
)
