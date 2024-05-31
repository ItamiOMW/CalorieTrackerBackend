package com.itami.service.auth

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.cloud.storage.Bucket
import com.itami.data.database.firebase.FirebaseStorageUrl
import com.itami.data.database.firebase.FirebaseStorageUrl.getDownloadUrl
import com.itami.data.database.firebase.FirebaseStorageUrl.reference
import com.itami.data.dto.request.*
import com.itami.data.dto.response.UserResponse
import com.itami.data.dto.response.UserWithTokenResponse
import com.itami.data.mapper.toCreateUserEmail
import com.itami.data.mapper.toCreateUserGoogle
import com.itami.data.mapper.toUpdateUser
import com.itami.data.mapper.toUserResponse
import com.itami.data.model.user.User
import com.itami.data.repository.user.UserRepository
import com.itami.utils.*

class AuthServiceImpl(
    private val userRepository: UserRepository,
    private val bucket: Bucket
) : AuthService {

    override suspend fun registerEmail(
        emailRegisterRequest: EmailRegisterRequest,
        profilePictureName: String?,
        profilePictureByteArray: ByteArray?
    ) {
        val userByEmail = userRepository.getUserByEmail(emailRegisterRequest.email)

        if (userByEmail != null) {
            throw AppException.ConflictException("A user with this email already exists.")
        }

        val profilePictureUrl = if (profilePictureName != null && profilePictureByteArray != null) {
            bucket.create(
                "${FirebaseStorageUrl.PROFILE_PICTURES_PATH}/$profilePictureName",
                profilePictureByteArray,
                "image/png"
            )
            FirebaseStorageUrl.BASE_PATH reference FirebaseStorageUrl.PROFILE_PICTURES_PATH getDownloadUrl profilePictureName
        } else {
            null
        }

        val createUserEmail = emailRegisterRequest.toCreateUserEmail(
            profilePictureUrl = profilePictureUrl,
            isActive = false,
        )

        val user = userRepository.createUserEmail(createUserEmail)
        val generatedActivationToken = TokenManager.generateActivationToken(emailRegisterRequest.email)
        val activationToken = userRepository.saveActivationToken(generatedActivationToken)

        EmailManager.sendConfirmationEmail(
            email = user.email,
            token = activationToken.token
        )
    }

    override suspend fun loginEmail(emailLoginRequest: EmailLoginRequest): UserWithTokenResponse {
        val user = userRepository.getUserByEmail(emailLoginRequest.email)
            ?: throw AppException.UnauthorizedException("User with this email does not exist.")

        if (!user.isActive) {
            throw AppException.ForbiddenException("Account is not activated.")
        }

        if (user.hashPassword == null) {
            throw AppException.UnauthorizedException("Invalid email or password.")
        }

        val passwordsMatched = PasswordUtil.checkPassword(emailLoginRequest.password, user.hashPassword)
        if (!passwordsMatched) {
            throw AppException.UnauthorizedException("Invalid email or password.")
        }

        val token = TokenManager.generateAuthToken(user)
        return UserWithTokenResponse(
            user = user.toUserResponse(),
            token = token,
        )
    }

    override suspend fun registerGoogle(googleRegisterRequest: GoogleRegisterRequest): UserWithTokenResponse {
        val idToken = verifyGoogleIdToken(googleRegisterRequest.googleIdToken)
            ?: throw AppException.BadRequestException("Invalid google id token.")

        val createUserGoogle = googleRegisterRequest.toCreateUserGoogle(
            googleId = idToken.payload["sub"].toString(),
            name = idToken.payload["name"].toString(),
            email = idToken.payload["email"].toString(),
            profilePictureUrl = idToken.payload["picture"].toString(),
            isActive = true
        )

        val userByEmail = userRepository.getUserByEmail(createUserGoogle.email)
        if (userByEmail != null) {
            throw AppException.ConflictException("User with this email already exists.")
        }

        val user = userRepository.createUserGoogle(createUserGoogle)
        val token = TokenManager.generateAuthToken(user)
        return UserWithTokenResponse(
            user = user.toUserResponse(),
            token = token,
        )
    }

    override suspend fun loginGoogle(googleLoginRequest: GoogleLoginRequest): UserWithTokenResponse {
        val idToken = verifyGoogleIdToken(googleLoginRequest.googleIdToken)
            ?: throw AppException.BadRequestException("Invalid google id token.")

        val user = getUserByGoogleId(idToken.payload["sub"].toString())
            ?: throw AppException.UnauthorizedException("User with this google account does not exist.")

        if (!user.isActive) {
            throw AppException.ForbiddenException("Account is not activated.")
        }

        val token = TokenManager.generateAuthToken(user)
        return UserWithTokenResponse(
            user = user.toUserResponse(),
            token = token,
        )
    }

    override suspend fun authenticate(userId: Int): UserResponse {
        val user = getUserById(userId) ?: throw AppException.UnauthorizedException("User not found.")

        if (!user.isActive) {
            throw AppException.ForbiddenException("Account is not activated.")
        }

        return user.toUserResponse()
    }

    override suspend fun activateUser(token: String) {
        val activationToken = userRepository.getActivationToken(token)

        if (activationToken == null || activationToken.expiresAt <= DateTimeUtil.currentDateTime()) {
            throw AppException.ConflictException("Invalid or expired activation token.")
        }

        val user = userRepository.getUserByEmail(activationToken.email)
            ?: throw AppException.NotFoundException("User with this email does not exist.")

        val updateUser = user.copy(isActive = true).toUpdateUser()
        userRepository.updateUser(userId = user.id, updateUser = updateUser)
    }

    override suspend fun resendActivationEmail(resendActivationEmailRequest: ResendActivationEmailRequest) {
        val email = resendActivationEmailRequest.email

        val user = userRepository.getUserByEmail(email)
            ?: throw AppException.NotFoundException("User with this email does not exist.")

        val generatedActivationToken = TokenManager.generateActivationToken(email)
        val activationToken = userRepository.saveActivationToken(generatedActivationToken)

        EmailManager.sendConfirmationEmail(
            email = user.email,
            token = activationToken.token
        )
    }

    override suspend fun resetPassword(resetPasswordRequest: ResetPasswordRequest) {
        val email = resetPasswordRequest.email

        val user = userRepository.getUserByEmail(email = email)
            ?: throw AppException.NotFoundException("User with this email does not exist.")

        val resetCode = userRepository.getPasswordResetCode(email = email, code = resetPasswordRequest.code)
        if (resetCode == null || resetCode.expiresAt <= DateTimeUtil.currentDateTime()) {
            throw AppException.ConflictException("Invalid or expired password reset code.")
        }

        val newHashPassword = PasswordUtil.hashPassword(resetPasswordRequest.newPassword)
        val updateUser = user.copy(hashPassword = newHashPassword).toUpdateUser()
        userRepository.updateUser(userId = user.id, updateUser = updateUser)
        userRepository.deletePasswordResetCode(resetCode.code, updateUser.email)
    }

    override suspend fun sendPasswordResetCode(sendPasswordResetCodeRequest: SendPasswordResetCodeRequest) {
        val email = sendPasswordResetCodeRequest.email

        val user = userRepository.getUserByEmail(email = email)
            ?: throw AppException.NotFoundException("User with this email does not exist.")

        val generatedResetCode = TokenManager.generatePasswordResetCode(email)
        val resetCode = userRepository.savePasswordResetCode(generatedResetCode)

        EmailManager.sendPasswordResetCode(
            email = user.email,
            code = resetCode.code
        )
    }

    override suspend fun getUserByGoogleId(googleId: String): User? {
        return userRepository.getUserByGoogleId(googleId)
    }

    override suspend fun getUserById(userId: Int): User? {
        return userRepository.getUserById(userId)
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

}