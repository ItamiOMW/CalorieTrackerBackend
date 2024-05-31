package com.itami.routes.auth

import com.itami.API_VERSION
import com.itami.data.dto.request.*
import com.itami.plugins.JWT_AUTH
import com.itami.service.auth.AuthService
import com.itami.utils.AppException
import com.itami.utils.convert
import com.itami.utils.userId
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json

private const val AUTH = "$API_VERSION/auth"
private const val EMAIL_REGISTER = "$AUTH/email/register"
private const val EMAIL_LOGIN = "$AUTH/email/login"
private const val GOOGLE_REGISTER = "$AUTH/google/register"
private const val GOOGLE_LOGIN = "$AUTH/google/login"
private const val ACTIVATE_ACCOUNT = "/activate/{token}"
private const val RESEND_ACTIVATION_EMAIL = "/activate/resend"
private const val RESET_PASSWORD = "$AUTH/reset/password"
private const val SEND_PASSWORD_RESET = "$AUTH/reset/password/send"

fun Route.auth(
    authService: AuthService,
) {
    post(EMAIL_REGISTER) {
        val multipart = call.receiveMultipart()

        var emailRegisterRequest: EmailRegisterRequest? = null
        var profilePictureName: String? = null
        var profilePictureByteArray: ByteArray? = null

        multipart.forEachPart { partData ->
            when (partData) {
                is PartData.FormItem -> {
                    if (partData.name == "email_register_request") {
                        emailRegisterRequest = Json.decodeFromString(partData.value)
                    }
                }

                is PartData.FileItem -> {
                    if (partData.name == "profile_picture") {
                        val (fileName, fileBytes) = partData.convert()
                        profilePictureName = fileName
                        profilePictureByteArray = fileBytes
                    }
                }

                else -> Unit
            }
        }

        emailRegisterRequest?.let {
            authService.registerEmail(
                emailRegisterRequest = it,
                profilePictureName = profilePictureName,
                profilePictureByteArray = profilePictureByteArray
            )
            call.respond(status = HttpStatusCode.Created, message = "Registration is successful, confirm email.")
        } ?: throw AppException.BadRequestException()
    }
    post(EMAIL_LOGIN) {
        val emailLoginRequest = call.receive<EmailLoginRequest>()
        val userWithToken = authService.loginEmail(emailLoginRequest)
        call.respond(status = HttpStatusCode.OK, message = userWithToken)
    }
    post(GOOGLE_REGISTER) {
        val googleRegisterRequest = call.receive<GoogleRegisterRequest>()
        val userWithToken = authService.registerGoogle(googleRegisterRequest)
        call.respond(status = HttpStatusCode.Created, message = userWithToken)
    }
    post(GOOGLE_LOGIN) {
        val googleLoginRequest = call.receive<GoogleLoginRequest>()
        val userWithToken = authService.loginGoogle(googleLoginRequest)
        call.respond(status = HttpStatusCode.OK, message = userWithToken)
    }
    get(ACTIVATE_ACCOUNT) {
        val token = call.parameters["token"] ?: throw AppException.BadRequestException()
        authService.activateUser(token)
        call.respond(status = HttpStatusCode.OK, message = "Account was successfully activated.")
    }
    post(RESEND_ACTIVATION_EMAIL) {
        val resendActivationEmailRequest = call.receive<ResendActivationEmailRequest>()
        authService.resendActivationEmail(resendActivationEmailRequest)
        call.respond(status = HttpStatusCode.OK, message = "Email has been sent successfully.")
    }
    post(RESET_PASSWORD) {
        val resetPasswordRequest = call.receive<ResetPasswordRequest>()
        authService.resetPassword(resetPasswordRequest)
        call.respond(status = HttpStatusCode.OK, message = "Password has been changed successfully.")
    }
    post(SEND_PASSWORD_RESET) {
        val sendPasswordResetCodeRequest = call.receive<SendPasswordResetCodeRequest>()
        authService.sendPasswordResetCode(sendPasswordResetCodeRequest)
        call.respond(status = HttpStatusCode.OK, message = "Password reset code has been sent.")
    }
    authenticate(JWT_AUTH) {
        get(AUTH) {
            val userId = call.userId()
            val userResponse = authService.authenticate(userId)
            call.respond(status = HttpStatusCode.OK, message = userResponse)
        }
    }
}