package com.itami.data.repository.user

import com.itami.data.model.user.CreateUser
import com.itami.data.model.user.User

interface UserRepository {

    suspend fun getUserById(userId: Int): User?

    suspend fun getUserByGoogleId(googleId: String): User?

    suspend fun getUserByEmail(email: String): User?

    suspend fun createUser(createUser: CreateUser): User

    suspend fun deleteUser(userId: Int)

}