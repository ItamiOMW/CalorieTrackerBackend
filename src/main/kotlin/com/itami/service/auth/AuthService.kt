package com.itami.service.auth

import com.itami.data.model.CreateUser
import com.itami.data.model.User
import com.itami.data.response.UserWithTokenResponse
import com.itami.data.response.UserResponse

interface AuthService {

    suspend fun register(createUser: CreateUser): UserWithTokenResponse

    suspend fun loginGoogle(userGoogleId: String): UserWithTokenResponse

    suspend fun authenticate(userId: Int): UserResponse

    suspend fun getUserByGoogleId(googleId: String): User?

    suspend fun getUserById(userId: Int): User?

}