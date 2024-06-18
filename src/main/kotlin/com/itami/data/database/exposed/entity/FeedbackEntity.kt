package com.itami.data.database.exposed.entity

import com.itami.data.database.exposed.table.Feedbacks
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class FeedbackEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<FeedbackEntity>(Feedbacks)

    var userId by Feedbacks.userId
    val user by UserEntity referencedOn Feedbacks.userId
    var message by Feedbacks.message
    var datetime by Feedbacks.datetime
}