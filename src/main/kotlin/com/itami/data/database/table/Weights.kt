package com.itami.data.database.table

import com.itami.utils.DateTimeUtil
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.datetime

object Weights : IntIdTable(name = "weights") {
    val weightGrams = integer("weight_grams")
    val userId = reference("user_id", Users.id, ReferenceOption.CASCADE)
    val datetime = datetime("date").default(DateTimeUtil.currentDateTime())
}