package com.itami.service.recipe

import com.itami.data.dto.response.RecipeResponse

interface RecipeService {

    suspend fun getRecipesByQuery(query: String, page: Int, pageSize: Int): List<RecipeResponse>

    suspend fun getRecipeById(recipeId: Int): RecipeResponse

}