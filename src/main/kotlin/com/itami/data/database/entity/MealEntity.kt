package com.itami.data.database.entity

import com.itami.data.database.table.ConsumedFoods
import com.itami.data.database.table.Meals
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class MealEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<MealEntity>(Meals)

    var name by Meals.name
    var user by UserEntity referencedOn Meals.userId
    val consumedFoods by ConsumedFoodsEntity referrersOn ConsumedFoods.mealId
    var createdAt by Meals.createdAt
}
