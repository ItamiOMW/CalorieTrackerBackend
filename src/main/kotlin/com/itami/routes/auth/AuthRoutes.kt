package com.itami.routes.auth

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.itami.API_VERSION
import com.itami.data.dto.request.GoogleLoginRequest
import com.itami.data.dto.request.GoogleRegisterRequest
import com.itami.data.model.user.CreateUser
import com.itami.plugins.JWT_AUTH
import com.itami.service.auth.AuthService
import com.itami.utils.AppException
import com.itami.utils.userId
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

private const val AUTH = "$API_VERSION/auth"
private const val GOOGLE_REGISTER = "$AUTH/google/register"
private const val GOOGLE_LOGIN = "$AUTH/google/login"

fun Route.auth(
    authService: AuthService
) {
    post(GOOGLE_REGISTER) {
        val googleRegisterRequest = call.receive<GoogleRegisterRequest>()
        val idToken = verifyGoogleIdToken(googleRegisterRequest.googleIdToken)
            ?: throw AppException.BadRequestException("Invalid google id token.")
        val googleId = idToken.payload["sub"].toString()
        val name = idToken.payload["name"].toString()
        val email = idToken.payload["email"].toString()
        val profilePictureUrl = idToken.payload["picture"].toString()
        val createUser = CreateUser(
            googleId = googleId,
            email = email,
            name = name,
            profilePictureUrl = profilePictureUrl,
            age = googleRegisterRequest.age,
            heightCm = googleRegisterRequest.heightCm,
            weightGoal = googleRegisterRequest.weightGoal,
            weightGrams = googleRegisterRequest.weightGrams,
            lifestyle = googleRegisterRequest.lifestyle,
            gender = googleRegisterRequest.gender,
            dailyCalories = googleRegisterRequest.dailyCalories,
            dailyProteins = googleRegisterRequest.dailyProteins,
            dailyFats = googleRegisterRequest.dailyFats,
            dailyCarbs = googleRegisterRequest.dailyCarbs,
            waterMl = googleRegisterRequest.waterMl
        )
        val userWithToken = authService.register(createUser = createUser)
        call.respond(status = HttpStatusCode.Created, message = userWithToken)
    }
    post(GOOGLE_LOGIN) {
        val googleLoginRequest = call.receive<GoogleLoginRequest>()
        val idToken = verifyGoogleIdToken(googleLoginRequest.googleIdToken)
            ?: throw AppException.BadRequestException("Invalid google id token.")
        val userGoogleId = idToken.payload["sub"].toString()
        val userWithToken = authService.loginGoogle(userGoogleId = userGoogleId)
        call.respond(status = HttpStatusCode.OK, message = userWithToken)
    }
    authenticate(JWT_AUTH) {
        get(AUTH) {
            val userId = call.userId()
            val userResponse = authService.authenticate(userId)
            call.respond(status = HttpStatusCode.OK, message = userResponse)
        }
    }
}

private fun verifyGoogleIdToken(idToken: String): GoogleIdToken? {
    return try {
        val verifier = GoogleIdTokenVerifier.Builder(NetHttpTransport(), GsonFactory())
            .setAudience(listOf(System.getenv("GOOGLE_CLIENT_ID")))
            .setIssuer("https://accounts.google.com")
            .build()

        verifier.verify(idToken)
    } catch (e: Exception) {
        return null
    }
}