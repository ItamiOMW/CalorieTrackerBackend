package com.itami.data.database.entity

import com.itami.data.database.table.ConsumedFoods
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ConsumedFoodsEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ConsumedFoodsEntity>(ConsumedFoods)

    var meal by MealEntity referencedOn ConsumedFoods.mealId
    var food by FoodEntity referencedOn ConsumedFoods.foodId
    var grams by ConsumedFoods.grams
}