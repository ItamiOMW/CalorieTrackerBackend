package com.itami.service.auth

import com.itami.data.model.user.CreateUser
import com.itami.data.model.user.User
import com.itami.data.dto.response.UserWithTokenResponse
import com.itami.data.dto.response.UserResponse

interface AuthService {

    suspend fun register(createUser: CreateUser): UserWithTokenResponse

    suspend fun loginGoogle(userGoogleId: String): UserWithTokenResponse

    suspend fun authenticate(userId: Int): UserResponse

    suspend fun getUserByGoogleId(googleId: String): User?

    suspend fun getUserById(userId: Int): User?

}