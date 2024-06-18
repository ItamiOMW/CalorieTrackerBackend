package com.itami.data.database.exposed.table

import com.itami.utils.DateTimeUtil
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.datetime

object Feedbacks: IntIdTable(name = "feedbacks") {
    val userId = reference("user_id", Users.id, ReferenceOption.CASCADE)
    val message = varchar("message", 1000)
    val datetime = datetime("datetime").default(DateTimeUtil.currentDateTime())
}