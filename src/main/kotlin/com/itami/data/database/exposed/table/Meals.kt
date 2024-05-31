package com.itami.data.database.exposed.table

import com.itami.utils.DateTimeUtil
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.datetime

object Meals : IntIdTable("meals") {
    val name = varchar("name", 255)
    val userId = reference("user_id", Users.id, ReferenceOption.CASCADE)
    val createdAt = datetime("timestamp").default(DateTimeUtil.currentDateTime())
}