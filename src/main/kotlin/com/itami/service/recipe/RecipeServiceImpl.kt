package com.itami.service.recipe

import com.itami.data.dto.response.RecipeResponse
import com.itami.data.mapper.toRecipeResponse
import com.itami.data.repository.recipe.RecipeRepository
import com.itami.utils.AppException

class RecipeServiceImpl(
    private val recipeRepository: RecipeRepository
) : RecipeService {

    override suspend fun getRecipesByQuery(query: String, page: Int, pageSize: Int): List<RecipeResponse> {
        return recipeRepository.getRecipeByQuery(
            query = query,
            page = page,
            pageSize = pageSize
        ).map { it.toRecipeResponse() }
    }

    override suspend fun getRecipeById(recipeId: Int): RecipeResponse {
        val recipe = recipeRepository.getRecipeById(recipeId = recipeId) ?: throw AppException.NotFoundException()
        return recipe.toRecipeResponse()
    }

}