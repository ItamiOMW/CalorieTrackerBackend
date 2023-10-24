package com.itami.presentation.mapper

import com.itami.domain.model.ConsumedFood
import com.itami.domain.model.Food
import com.itami.domain.model.Meal
import com.itami.presentation.model.response.ConsumedFoodResponse
import com.itami.presentation.model.response.FoodResponse
import com.itami.presentation.model.response.MealResponse
import com.itami.utils.toLong

fun Meal.toMealResponse() = MealResponse(
    id = this.id,
    name = this.name,
    userId = this.user.id,
    consumedFoods = this.consumedFoods.map { it.toConsumedFoodResponse() },
    createdAt = this.createdAt.toLong()
)

fun Food.toFoodResponse() = FoodResponse(
    id = this.id,
    name = this.name,
    caloriesIn100Grams = this.caloriesIn100Grams,
    proteinsIn100Grams = this.proteinsIn100Grams,
    fatsIn100Grams = this.fatsIn100Grams,
    carbsIn100Grams = this.carbsIn100Grams
)

fun ConsumedFood.toConsumedFoodResponse() = ConsumedFoodResponse(
    id = this.id,
    food = this.food.toFoodResponse(),
    grams = this.grams
)