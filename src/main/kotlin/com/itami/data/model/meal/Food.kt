package com.itami.data.model.meal

data class Food(
    val id: Int,
    val name: String,
    val caloriesIn100Grams: Int,
    val proteinsIn100Grams: Int,
    val fatsIn100Grams: Int,
    val carbsIn100Grams: Int,
)
