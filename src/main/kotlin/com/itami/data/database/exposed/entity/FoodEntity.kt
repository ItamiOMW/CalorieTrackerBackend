package com.itami.data.database.exposed.entity

import com.itami.data.database.exposed.table.FoodTranslations
import com.itami.data.database.exposed.table.Foods
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class FoodEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<FoodEntity>(Foods)

    var name by Foods.name
    val translations by FoodTranslationEntity referrersOn FoodTranslations.foodId
    var caloriesIn100Grams by Foods.caloriesIn100Grams
    var proteinsIn100Grams by Foods.proteinsIn100Grams
    var fatsIn100Grams by Foods.fatsIn100Grams
    var carbsIn100Grams by Foods.carbsIn100Grams
}