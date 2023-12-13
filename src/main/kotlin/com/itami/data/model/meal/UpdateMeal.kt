package com.itami.data.model.meal

data class UpdateMeal(
    val name: String,
    val consumedFoods: List<CreateConsumedFood>,
)