package com.itami.utils

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun ApplicationCall.userId(): Int =
    this.principal<JWTPrincipal>()?.getClaim("id", Int::class) ?: throw AppException.UnauthorizedException()