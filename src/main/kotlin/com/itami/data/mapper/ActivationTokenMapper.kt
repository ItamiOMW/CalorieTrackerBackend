package com.itami.data.mapper

import com.itami.data.database.exposed.entity.ActivationTokenEntity
import com.itami.data.model.user.ActivationToken

fun ActivationTokenEntity.toActivationToken() = ActivationToken(
    token = this.token,
    email = this.userEmail,
    expiresAt = this.expiresAt
)