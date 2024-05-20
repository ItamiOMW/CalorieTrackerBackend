package com.itami.data.database.entity

import com.itami.data.database.table.Weights
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class WeightEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<WeightEntity>(Weights)

    var weightGrams by Weights.weightGrams
    var user by UserEntity referencedOn Weights.userId
    var datetime by Weights.datetime
}