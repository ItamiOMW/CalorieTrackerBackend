package com.itami.service.meal

import com.itami.data.dto.request.AddConsumedWaterRequest
import com.itami.data.dto.request.CreateMealRequest
import com.itami.data.dto.request.RemoveConsumedWaterRequest
import com.itami.data.dto.request.UpdateMealRequest
import com.itami.data.dto.response.ConsumedWaterResponse
import com.itami.data.dto.response.MealResponse
import com.itami.data.dto.response.MealsWithConsumedWaterResponse
import com.itami.data.mapper.toConsumedWaterResponse
import com.itami.data.mapper.toCreateMeal
import com.itami.data.mapper.toMealResponse
import com.itami.data.mapper.toUpdateMeal
import com.itami.data.repository.meal.MealRepository
import com.itami.utils.AppException
import com.itami.utils.DateTimeUtil

class MealServiceImpl(
    private val mealRepository: MealRepository
) : MealService {

    override suspend fun getSummary(userId: Int, date: String): MealsWithConsumedWaterResponse {
        val localDate = DateTimeUtil.stringToDate(date)
        val meals = mealRepository.getMealsByDate(
            userId = userId,
            date = localDate
        ).map { it.toMealResponse() }
        val consumedWater = mealRepository.getConsumedWater(
            userId = userId,
            date = localDate
        )?.toConsumedWaterResponse()

        return MealsWithConsumedWaterResponse(meals = meals, consumedWater = consumedWater)
    }

    override suspend fun getMeal(userId: Int, mealId: Int): MealResponse {
        val meal = mealRepository.getMealById(mealId = mealId) ?: throw AppException.NotFoundException()
        if (meal.user.id != userId) {
            throw AppException.ForbiddenException()
        }
        return meal.toMealResponse()
    }

    override suspend fun getMeals(userId: Int, date: String): List<MealResponse> {
        return mealRepository.getMealsByDate(userId = userId, date = DateTimeUtil.stringToDate(date))
            .map { it.toMealResponse() }
    }

    override suspend fun getMealById(userId: Int, mealId: Int): MealResponse {
        val meal = mealRepository.getMealById(mealId = mealId) ?: throw AppException.NotFoundException()
        if (meal.user.id != userId) {
            throw AppException.ForbiddenException()
        }
        return meal.toMealResponse()
    }

    override suspend fun createMeal(userId: Int, createMealRequest: CreateMealRequest): MealResponse {
        return mealRepository.createMeal(createMeal = createMealRequest.toCreateMeal(userId = userId))?.toMealResponse()
            ?: throw Exception("Unknown error occurred.")
    }

    override suspend fun updateMeal(userId: Int, mealId: Int, updateMealRequest: UpdateMealRequest): MealResponse {
        val meal = mealRepository.getMealById(mealId = mealId) ?: throw AppException.NotFoundException("Meal not found")
        if (meal.user.id != userId) {
            throw AppException.ForbiddenException()
        }
        val updatedMeal = mealRepository.updateMeal(
            mealId = mealId,
            updateMeal = updateMealRequest.toUpdateMeal()
        ) ?: throw Exception("Unknown error occurred.")
        return updatedMeal.toMealResponse()
    }

    override suspend fun deleteMeal(userId: Int, mealId: Int) {
        val meal = mealRepository.getMealById(mealId) ?: throw AppException.NotFoundException("Meal not found")
        if (meal.user.id != userId) {
            throw AppException.ForbiddenException()
        }
        mealRepository.deleteMeal(mealId = mealId)
    }

    override suspend fun addConsumedWater(
        userId: Int,
        addConsumedWaterRequest: AddConsumedWaterRequest
    ): ConsumedWaterResponse {
        val localDateTime = DateTimeUtil.stringToDateTime(addConsumedWaterRequest.datetime)
        val localDate = localDateTime.toLocalDate()
        val waterMl = addConsumedWaterRequest.waterMl
        val consumedWater = mealRepository.getConsumedWater(userId = userId, date = localDate)

        if (consumedWater == null) {
            val createdConsumedWater = mealRepository.createConsumedWater(
                userId = userId,
                timestamp = localDateTime,
                waterMl = waterMl
            ) ?: throw Exception()
            return createdConsumedWater.toConsumedWaterResponse()
        }

        val updatedConsumedWater = mealRepository.updateConsumedWater(
            userId = userId,
            timestamp = localDateTime,
            waterMl = consumedWater.waterMl + waterMl
        ) ?: throw Exception()
        return updatedConsumedWater.toConsumedWaterResponse()
    }

    override suspend fun removeConsumedWater(
        userId: Int,
        removeConsumedWaterRequest: RemoveConsumedWaterRequest
    ): ConsumedWaterResponse {
        val localDateTime = DateTimeUtil.stringToDateTime(removeConsumedWaterRequest.datetime)
        val localDate = localDateTime.toLocalDate()
        val waterMlToRemove = removeConsumedWaterRequest.waterMlToRemove
        val consumedWater = mealRepository.getConsumedWater(
            userId = userId,
            date = localDate
        )

        if (consumedWater == null) {
            val createdConsumedWater = mealRepository.createConsumedWater(
                userId = userId,
                timestamp = localDateTime,
                waterMl = 0
            ) ?: throw Exception()
            return createdConsumedWater.toConsumedWaterResponse()
        }

        val updatedConsumedWater = mealRepository.updateConsumedWater(
            userId = userId,
            timestamp = localDateTime,
            waterMl = (consumedWater.waterMl - waterMlToRemove).coerceAtLeast(0)
        ) ?: throw Exception()
        return updatedConsumedWater.toConsumedWaterResponse()
    }
}