package com.itami.data.repository.food

import com.itami.data.model.meal.Food

interface FoodRepository {

    suspend fun getFoodByQuery(query: String, page: Int, pageSize: Int): List<Food>

    suspend fun getFoodsByIds(ids: List<Int>): List<Food>

}