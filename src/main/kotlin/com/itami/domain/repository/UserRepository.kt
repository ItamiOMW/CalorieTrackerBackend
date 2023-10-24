package com.itami.domain.repository

import com.itami.domain.model.User

interface UserRepository {

    suspend fun getUserById(userId: Int): User?

    suspend fun getUserByGoogleId(googleId: String): User?

    suspend fun createUser(user: User): User

    suspend fun deleteUser(userId: Int)

}