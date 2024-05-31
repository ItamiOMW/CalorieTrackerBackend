package com.itami.service.auth

import com.itami.data.dto.request.*
import com.itami.data.model.user.User
import com.itami.data.dto.response.UserWithTokenResponse
import com.itami.data.dto.response.UserResponse

interface AuthService {

    suspend fun registerEmail(
        emailRegisterRequest: EmailRegisterRequest,
        profilePictureName: String? = null,
        profilePictureByteArray: ByteArray? = null
    )

    suspend fun loginEmail(emailLoginRequest: EmailLoginRequest): UserWithTokenResponse

    suspend fun registerGoogle(googleRegisterRequest: GoogleRegisterRequest): UserWithTokenResponse

    suspend fun loginGoogle(googleLoginRequest: GoogleLoginRequest): UserWithTokenResponse

    suspend fun authenticate(userId: Int): UserResponse

    suspend fun activateUser(token: String)

    suspend fun resendActivationEmail(resendActivationEmailRequest: ResendActivationEmailRequest)

    suspend fun resetPassword(resetPasswordRequest: ResetPasswordRequest)

    suspend fun sendPasswordResetCode(sendPasswordResetCodeRequest: SendPasswordResetCodeRequest)

    suspend fun getUserByGoogleId(googleId: String): User?

    suspend fun getUserById(userId: Int): User?

}