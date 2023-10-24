package com.itami.domain.model

data class ConsumedFood(
    val id: Int,
    val food: Food,
    val grams: Int,
)