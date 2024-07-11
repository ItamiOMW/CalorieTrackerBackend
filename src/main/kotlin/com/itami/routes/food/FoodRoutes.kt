package com.itami.routes.food

import com.itami.API_VERSION
import com.itami.plugins.JWT_AUTH
import com.itami.service.food.FoodService
import com.itami.utils.Constants
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

private const val FOODS = "$API_VERSION/foods"


fun Route.food(
    foodService: FoodService
) {
    authenticate(JWT_AUTH) {
        get(FOODS) {
            val queryParams = call.request.queryParameters
            val query = queryParams["query"] ?: ""
            val page = queryParams["page"]?.toIntOrNull() ?: 1
            val pageSize = queryParams["pageSize"]?.toIntOrNull() ?: Constants.DEFAULT_PAGE_SIZE
            val languageCode = call.request.headers["Accept-Language"] ?: Constants.DEFAULT_LANGUAGE_CODE
            val foods = foodService.searchFood(
                query = query,
                page = page,
                pageSize = pageSize,
                languageCode = languageCode
            )
            call.respond(status = HttpStatusCode.OK, message = foods)
        }
    }
}