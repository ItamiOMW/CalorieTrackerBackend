package com.itami.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.itami.data.model.User
import java.util.*

object TokenManager {

    private val secret = System.getenv("JWT_SECRET")
    private val issuer = System.getenv("JWT_ISSUER")
    private val audience = System.getenv("JWT_AUDIENCE")
    private val algorithm = Algorithm.HMAC256(secret)

    val tokenVerifier: JWTVerifier = JWT
        .require(algorithm)
        .withAudience(audience)
        .withIssuer(issuer)
        .build()

    fun generateToken(user: User): String {
        return JWT.create()
            .withIssuer(issuer)
            .withAudience(audience)
            .withClaim("id", user.id)
            .withClaim("email", user.email)
            .withExpiresAt(Date(System.currentTimeMillis() + Constants.MILLIS_IN_YEAR))
            .sign(algorithm)
    }
}