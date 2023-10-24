package com.itami.data.mapper

import com.itami.data.database.entity.ConsumedFoodsEntity
import com.itami.data.database.entity.FoodEntity
import com.itami.data.database.entity.MealEntity
import com.itami.domain.model.ConsumedFood
import com.itami.domain.model.Food
import com.itami.domain.model.Meal


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