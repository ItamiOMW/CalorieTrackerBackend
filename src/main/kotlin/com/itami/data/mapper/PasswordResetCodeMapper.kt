package com.itami.data.mapper

import com.itami.data.database.exposed.entity.PasswordResetCodeEntity
import com.itami.data.model.user.PasswordResetCode

fun PasswordResetCodeEntity.toPasswordResetCode() = PasswordResetCode(
    code = this.code,
    email = this.userEmail,
    expiresAt = this.expiresAt
)