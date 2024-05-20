package com.itami.data.model.meal

import java.time.LocalDate

data class CaloriesEaten(
    val date: LocalDate,
    val caloriesEaten: Int,
    val proteinsEaten: Int,
    val fatsEaten: Int,
    val carbsEaten: Int,
)
