package com.itami.service.recipe

import com.itami.data.dto.response.RecipeResponse
import com.itami.data.model.recipe.CaloriesFilter
import com.itami.data.model.recipe.TimeFilter

interface RecipeService {

    suspend fun getRecipesByQuery(
        query: String,
        page: Int,
        pageSize: Int,
        languageCode: String,
        timeFilters: List<TimeFilter>?,
        caloriesFilters: List<CaloriesFilter>?,
    ): List<RecipeResponse>

    suspend fun getRecipeById(
        recipeId: Int,
        languageCode: String,
    ): RecipeResponse

}