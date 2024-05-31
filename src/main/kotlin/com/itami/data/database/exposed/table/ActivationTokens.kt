package com.itami.data.database.exposed.table

import com.itami.utils.DateTimeUtil
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.datetime

object ActivationTokens : IntIdTable() {
    val token = varchar("token", 255)
    val userEmail = reference("user_email", Users.email, ReferenceOption.CASCADE)
    val expiresAt = datetime("expires_at").default(DateTimeUtil.currentDateTime().plusMinutes(15))
}