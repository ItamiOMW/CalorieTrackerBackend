package com.itami.service.auth

import com.itami.data.dto.response.UserResponse
import com.itami.data.dto.response.UserWithTokenResponse
import com.itami.data.mapper.toUserResponse
import com.itami.data.model.user.CreateUser
import com.itami.data.model.user.User
import com.itami.data.repository.user.UserRepository
import com.itami.utils.AppException
import com.itami.utils.TokenManager

class AuthServiceImpl(
    private val userRepository: UserRepository
) : AuthService {

    override suspend fun register(createUser: CreateUser): UserWithTokenResponse {
        val userByEmail = userRepository.getUserByEmail(createUser.email)
        if (userByEmail != null) {
            throw AppException.ConflictException("A user with this email already exists.")
        }
        val user = userRepository.createUser(createUser)
        val token = TokenManager.generateToken(user)
        return UserWithTokenResponse(
            user = user.toUserResponse(),
            token = token,
        )
    }

    override suspend fun loginGoogle(userGoogleId: String): UserWithTokenResponse {
        val user = getUserByGoogleId(userGoogleId)
            ?: throw AppException.UnauthorizedException("User with this google account does not exist.")
        val token = TokenManager.generateToken(user)
        return UserWithTokenResponse(
            user = user.toUserResponse(),
            token = token,
        )
    }

    override suspend fun authenticate(userId: Int): UserResponse {
        val user = getUserById(userId) ?: throw AppException.UnauthorizedException("User not found.")
        return user.toUserResponse()
    }

    override suspend fun getUserByGoogleId(googleId: String): User? {
        return userRepository.getUserByGoogleId(googleId)
    }

    override suspend fun getUserById(userId: Int): User? {
        return userRepository.getUserById(userId)
    }
}