package com.itami.data.database.exposed.entity

import com.itami.data.database.exposed.table.ConsumedFoods
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ConsumedFoodEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ConsumedFoodEntity>(ConsumedFoods)

    var meal by MealEntity referencedOn ConsumedFoods.mealId
    var food by FoodEntity referencedOn ConsumedFoods.foodId
    var grams by ConsumedFoods.grams
}