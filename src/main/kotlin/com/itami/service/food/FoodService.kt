package com.itami.service.food

import com.itami.data.dto.response.FoodResponse

interface FoodService {

    suspend fun searchFood(query: String, page: Int, pageSize: Int): List<FoodResponse>

}