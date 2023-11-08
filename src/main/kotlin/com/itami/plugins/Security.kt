package com.itami.plugins

import com.itami.utils.AppException
import com.itami.utils.TokenManager
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*


const val JWT_AUTH = "jwt-auth"

fun Application.configureSecurity() {
    val config = environment.config
    val realm = config.property("jwt.realm").getString()
    install(Authentication) {
        jwt(JWT_AUTH) {
            this.realm = realm
            verifier(TokenManager.tokenVerifier)
            validate { jwtCredential ->
                if (jwtCredential.payload.getClaim("email").asString() != null &&
                    jwtCredential.payload.getClaim("id").asLong() != null
                ) {
                    JWTPrincipal(jwtCredential.payload)
                } else {
                    null
                }
            }
            challenge { defaultScheme, realm ->
                throw AppException.UnauthorizedException("Invalid token")
            }
        }
    }
}