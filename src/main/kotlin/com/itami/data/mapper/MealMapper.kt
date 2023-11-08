package com.itami.data.mapper

import com.itami.data.database.entity.ConsumedFoodsEntity
import com.itami.data.database.entity.FoodEntity
import com.itami.data.database.entity.MealEntity
import com.itami.data.model.ConsumedFood
import com.itami.data.model.Food
import com.itami.data.model.Meal
import com.itami.data.response.ConsumedFoodResponse
import com.itami.data.response.FoodResponse
import com.itami.data.response.MealResponse
import com.itami.utils.toLong


fun MealEntity.toMeal() = Meal(
    id = this.id.value,
    name = this.name,
    user = this.user.toUser(),
    consumedFoods = this.consumedFoods.map { it.toConsumedFood() },
    createdAt = this.createdAt
)

fun FoodEntity.toFood() = Food(
    id = this.id.value,
    name = this.name,
    caloriesIn100Grams = this.caloriesIn100Grams,
    proteinsIn100Grams = this.proteinsIn100Grams,
    fatsIn100Grams = this.fatsIn100Grams,
    carbsIn100Grams = this.carbsIn100Grams
)

fun ConsumedFoodsEntity.toConsumedFood() = ConsumedFood(
    id = this.id.value,
    food = this.food.toFood(),
    grams = this.grams
)

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