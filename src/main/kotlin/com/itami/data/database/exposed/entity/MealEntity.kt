package com.itami.data.database.exposed.entity

import com.itami.data.database.exposed.table.ConsumedFoods
import com.itami.data.database.exposed.table.Meals
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class MealEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<MealEntity>(Meals)

    var name by Meals.name
    var user by UserEntity referencedOn Meals.userId
    val consumedFoods by ConsumedFoodEntity referrersOn ConsumedFoods.mealId
    var createdAt by Meals.createdAt
}
