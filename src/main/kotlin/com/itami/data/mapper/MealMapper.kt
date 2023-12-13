package com.itami.data.mapper

import com.itami.data.database.entity.ConsumedFoodEntity
import com.itami.data.database.entity.ConsumedWaterEntity
import com.itami.data.database.entity.FoodEntity
import com.itami.data.database.entity.MealEntity
import com.itami.data.dto.request.ConsumedFoodRequest
import com.itami.data.dto.request.CreateMealRequest
import com.itami.data.dto.request.UpdateMealRequest
import com.itami.data.dto.response.ConsumedFoodResponse
import com.itami.data.dto.response.ConsumedWaterResponse
import com.itami.data.dto.response.FoodResponse
import com.itami.data.dto.response.MealResponse
import com.itami.data.model.meal.*
import com.itami.utils.DateTimeUtil


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

fun ConsumedFoodEntity.toConsumedFood() = ConsumedFood(
    id = this.id.value,
    food = this.food.toFood(),
    grams = this.grams
)

fun Meal.toMealResponse() = MealResponse(
    id = this.id,
    name = this.name,
    userId = this.user.id,
    consumedFoods = this.consumedFoods.map { it.toConsumedFoodResponse() },
    createdAt = DateTimeUtil.datetimeToString(this.createdAt)
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

fun CreateMealRequest.toCreateMeal(userId: Int) = CreateMeal(
    name = this.name,
    userId = userId,
    consumedFoods = this.consumedFoods.map { it.toCreateConsumedFood() },
    createAt = DateTimeUtil.stringToDateTime(this.datetime)
)

fun UpdateMealRequest.toUpdateMeal() = UpdateMeal(
    name = this.name,
    consumedFoods = this.consumedFoods.map { it.toCreateConsumedFood() },
)

fun ConsumedFoodRequest.toCreateConsumedFood() = CreateConsumedFood(
    foodId = this.foodId,
    grams = this.grams
)

fun ConsumedWaterEntity.toConsumedWater() = ConsumedWater(
    id = this.id.value,
    waterMl = this.waterMl,
    timestamp = this.timestamp,
    user = this.user.toUser()
)

fun ConsumedWater.toConsumedWaterResponse() = ConsumedWaterResponse(
    id = this.id,
    waterMl = this.waterMl,
    timestamp = DateTimeUtil.datetimeToString(this.timestamp),
    userId = this.user.id,
)