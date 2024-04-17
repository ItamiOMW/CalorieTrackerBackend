package com.itami.data.model.recipe

import com.itami.utils.Constants

data class Recipe(
    val id: Int = Constants.UNKNOWN_ID,
    val name: String,
    val recipeText: String,
    val caloriePerServing: Int,
    val proteinsPerServing: Int,
    val fatsPerServing: Int,
    val carbsPerServing: Int,
    val timeMinutes: Int,
    val imageUrl: String,
)
