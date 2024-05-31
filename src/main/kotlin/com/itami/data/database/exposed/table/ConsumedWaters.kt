package com.itami.data.database.exposed.table

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.datetime

object ConsumedWaters : IntIdTable("consumed_waters") {
    val waterMl = integer("water_ml").default(0)
    val timestamp = datetime("timestamp")
    val userId = reference("user_id", Users.id, ReferenceOption.CASCADE)
}