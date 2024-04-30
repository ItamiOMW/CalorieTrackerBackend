package com.itami.routes.recipe

import com.itami.API_VERSION
import com.itami.data.model.recipe.CaloriesFilter
import com.itami.data.model.recipe.TimeFilter
import com.itami.plugins.JWT_AUTH
import com.itami.service.recipe.RecipeService
import com.itami.utils.AppException
import com.itami.utils.Constants
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

private const val RECIPES = "$API_VERSION/recipes"
private const val RECIPE_BY_ID = "$RECIPES/{recipeId}"

fun Route.recipes(
    recipeService: RecipeService
) {
    authenticate(JWT_AUTH) {
        get(RECIPES) {
            val queryParams = call.request.queryParameters
            val query = queryParams["query"] ?: ""
            val page = queryParams["page"]?.toIntOrNull() ?: 1
            val pageSize = queryParams["pageSize"]?.toIntOrNull() ?: Constants.DEFAULT_PAGE_SIZE
            val caloriesFilters = queryParams["caloriesFilters"]?.split(",")?.map { CaloriesFilter.valueOf(it) }
            val timeFilters = queryParams.getAll("timeFilters")?.map { TimeFilter.valueOf(it) }
            val recipes = recipeService.getRecipesByQuery(
                query = query,
                page = page,
                pageSize = pageSize,
                caloriesFilters = caloriesFilters,
                timeFilters = timeFilters
            )
            call.respond(status = HttpStatusCode.OK, message = recipes)
        }
        get(RECIPE_BY_ID) {
            val recipeId = call.parameters["recipeId"]?.toIntOrNull() ?: throw AppException.BadRequestException()
            val recipe = recipeService.getRecipeById(recipeId = recipeId)
            call.respond(status = HttpStatusCode.OK, message = recipe)
        }
    }
}