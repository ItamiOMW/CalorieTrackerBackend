package com.itami.data.database.exposed.entity

import com.itami.data.database.exposed.table.FoodTranslations
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class FoodTranslationEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<FoodTranslationEntity>(FoodTranslations)

    val food by FoodEntity referencedOn FoodTranslations.foodId
    var foodId by FoodTranslations.foodId
    var languageCode by FoodTranslations.languageCode
    var translatedName by FoodTranslations.translatedName
}