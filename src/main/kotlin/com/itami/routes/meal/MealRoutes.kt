package com.itami.routes.meal

import com.itami.API_VERSION
import com.itami.data.dto.request.AddConsumedWaterRequest
import com.itami.data.dto.request.CreateMealRequest
import com.itami.data.dto.request.RemoveConsumedWaterRequest
import com.itami.data.dto.request.UpdateMealRequest
import com.itami.plugins.JWT_AUTH
import com.itami.service.meal.MealService
import com.itami.utils.AppException
import com.itami.utils.userId
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.*

private const val SUMMARY = "$API_VERSION/summary"

private const val MEALS = "$API_VERSION/meals"
private const val MEAL_BY_ID = "$MEALS/{mealId}"

private const val WATER = "$API_VERSION/water"
private const val WATER_ADD = "$WATER/add"
private const val WATER_REMOVE = "$WATER/remove"

fun Route.meal(
    mealService: MealService
) {
    authenticate(JWT_AUTH) {
        get(SUMMARY) {
            val userId = call.userId()
            val encodedDatetime = call.parameters["encoded_datetime"] ?: throw AppException.BadRequestException()
            val datetime = encodedDatetime.decodeBase64String()
            val mealsWithConsumedWater = mealService.getSummary(userId, datetime)
            call.respond(status = HttpStatusCode.OK, message = mealsWithConsumedWater)
        }
        get(MEALS) { _ ->
            val userId = call.userId()
            val encodedDatetime = call.parameters["encoded_datetime"] ?: throw AppException.BadRequestException()
            val datetime = encodedDatetime.decodeBase64String()
            val meals = mealService.getMeals(userId = userId, date = datetime)
            call.respond(status = HttpStatusCode.OK, message = meals)
        }
        post(MEALS) { _ ->
            val userId = call.userId()
            val createMealRequest = call.receive<CreateMealRequest>()
            val meal = mealService.createMeal(userId = userId, createMealRequest = createMealRequest)
            call.respond(status = HttpStatusCode.Created, message = meal)
        }
        get(MEAL_BY_ID) {
            val userId = call.userId()
            val mealId = call.parameters["mealId"]?.toIntOrNull() ?: throw AppException.BadRequestException()
            val meal = mealService.getMealById(userId = userId, mealId = mealId)
            call.respond(status = HttpStatusCode.OK, message = meal)
        }
        put(MEAL_BY_ID) {
            val userId = call.userId()
            val mealId = call.parameters["mealId"]?.toIntOrNull() ?: throw AppException.BadRequestException()
            val updateMealRequest = call.receive<UpdateMealRequest>()
            val meal = mealService.updateMeal(userId = userId, mealId = mealId, updateMealRequest)
            call.respond(status = HttpStatusCode.OK, message = meal)
        }
        delete(MEAL_BY_ID) {
            val userId = call.userId()
            val mealId = call.parameters["mealId"]?.toIntOrNull() ?: throw AppException.BadRequestException()
            mealService.deleteMeal(userId, mealId)
            call.respond(HttpStatusCode.OK)
        }
        put(WATER_ADD) {
            val userId = call.userId()
            val addConsumedWaterRequest = call.receive<AddConsumedWaterRequest>()
            val consumedWater = mealService.addConsumedWater(userId, addConsumedWaterRequest)
            call.respond(status = HttpStatusCode.OK, message = consumedWater)
        }
        put(WATER_REMOVE) {
            val userId = call.userId()
            val removeConsumedWaterRequest = call.receive<RemoveConsumedWaterRequest>()
            val consumedWater = mealService.removeConsumedWater(userId, removeConsumedWaterRequest)
            call.respond(status = HttpStatusCode.OK, message = consumedWater)
        }
    }
}