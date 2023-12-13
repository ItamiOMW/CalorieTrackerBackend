package com.itami.data.database.entity

import com.itami.data.database.table.ConsumedWaters
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ConsumedWaterEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ConsumedWaterEntity>(ConsumedWaters)

    var waterMl by ConsumedWaters.waterMl
    var timestamp by ConsumedWaters.timestamp
    var user by UserEntity referencedOn ConsumedWaters.userId
}