package com.itami.service.recipe

import com.itami.data.dto.response.RecipeResponse
import com.itami.data.mapper.toRecipeResponse
import com.itami.data.model.recipe.CaloriesFilter
import com.itami.data.model.recipe.TimeFilter
import com.itami.data.repository.recipe.RecipeRepository
import com.itami.utils.AppException

class RecipeServiceImpl(
    private val recipeRepository: RecipeRepository
) : RecipeService {

    override suspend fun getRecipesByQuery(
        query: String,
        page: Int,
        pageSize: Int,
        languageCode: String,
        timeFilters: List<TimeFilter>?,
        caloriesFilters: List<CaloriesFilter>?
    ): List<RecipeResponse> {
        return recipeRepository.getRecipeByQuery(
            query = query,
            page = page,
            pageSize = pageSize,
            languageCode = languageCode,
            timeFilters = timeFilters,
            caloriesFilters = caloriesFilters
        ).map { it.toRecipeResponse() }
    }

    override suspend fun getRecipeById(recipeId: Int, languageCode: String): RecipeResponse {
        val recipe = recipeRepository.getRecipeById(
            recipeId = recipeId,
            languageCode = languageCode
        ) ?: throw AppException.NotFoundException()
        return recipe.toRecipeResponse()
    }

}