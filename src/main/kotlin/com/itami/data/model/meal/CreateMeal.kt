package com.itami.data.model.meal

import java.time.LocalDateTime

data class CreateMeal(
    val name: String,
    val userId: Int,
    val consumedFoods: List<CreateConsumedFood>,
    val createAt: LocalDateTime
)