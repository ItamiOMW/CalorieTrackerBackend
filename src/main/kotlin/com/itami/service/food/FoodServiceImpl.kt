package com.itami.service.food

import com.itami.data.dto.response.FoodResponse
import com.itami.data.mapper.toFoodResponse
import com.itami.data.repository.food.FoodRepository

class FoodServiceImpl(
    private val foodRepository: FoodRepository
): FoodService {

    override suspend fun searchFood(query: String, page: Int, pageSize: Int): List<FoodResponse> {
        return foodRepository.getFoodByQuery(
            query = query,
            page = page,
            pageSize = pageSize
        ).map { it.toFoodResponse() }
    }

}