package com.itami.data.repository.recipe

import com.itami.data.model.recipe.Recipe

interface RecipeRepository {

    suspend fun getRecipeByQuery(
        query: String,
        page: Int,
        pageSize: Int,
        caloriesPerServingFrom: Int,
        caloriesPerServingTo: Int,
        timeCookingFromMin: Int,
        timeCookingToMin: Int
    ): List<Recipe>

    suspend fun getRecipeById(recipeId: Int): Recipe?

}