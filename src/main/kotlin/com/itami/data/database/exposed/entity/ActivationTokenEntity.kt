package com.itami.data.database.exposed.entity

import com.itami.data.database.exposed.table.ActivationTokens
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ActivationTokenEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ActivationTokenEntity>(ActivationTokens)

    var token by ActivationTokens.token
    var userEmail by ActivationTokens.userEmail
    var expiresAt by ActivationTokens.expiresAt
}
