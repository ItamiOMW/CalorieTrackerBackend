package com.itami.data.database.exposed.entity

import com.itami.data.database.exposed.table.PasswordResetCodes
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class PasswordResetCodeEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<PasswordResetCodeEntity>(PasswordResetCodes)

    var code by PasswordResetCodes.code
    var userEmail by PasswordResetCodes.userEmail
    var expiresAt by PasswordResetCodes.expiresAt
}
