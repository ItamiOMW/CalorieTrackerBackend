package com.itami.data.mapper

import com.itami.data.database.entity.RecipeEntity
import com.itami.data.dto.response.RecipeResponse
import com.itami.data.model.recipe.Recipe

fun Recipe.toRecipeResponse() = RecipeResponse(
    id = this.id,
    name = this.name,
    recipeText = this.recipeText,
    caloriePerServing = this.caloriePerServing,
    proteinsPerServing = this.proteinsPerServing,
    fatsPerServing = this.fatsPerServing,
    carbsPerServing = this.carbsPerServing,
    timeMinutes = this.timeMinutes,
    imageUrl = this.imageUrl
)

fun RecipeEntity.toRecipe() = Recipe(
    id = this.id.value,
    name = this.name,
    recipeText = this.recipeText,
    caloriePerServing = this.caloriesPerServing,
    proteinsPerServing = this.proteinsPerServing,
    fatsPerServing = this.fatsPerServing,
    carbsPerServing = this.carbsPerServing,
    timeMinutes = this.timeMinutes,
    imageUrl = this.imageUrl
)