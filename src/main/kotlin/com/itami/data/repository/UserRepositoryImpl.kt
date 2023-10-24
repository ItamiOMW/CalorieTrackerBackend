package com.itami.data.repository

import com.itami.data.database.DatabaseFactory.dbQuery
import com.itami.data.database.entity.UserEntity
import com.itami.data.database.table.Users
import com.itami.data.mapper.toUser
import com.itami.domain.model.User
import com.itami.domain.repository.UserRepository

class UserRepositoryImpl : UserRepository {

    override suspend fun getUserById(userId: Int): User? {
        return dbQuery {
            UserEntity.findById(userId)?.toUser()
        }
    }

    override suspend fun getUserByGoogleId(googleId: String): User? {
        return dbQuery {
            UserEntity.find { Users.googleId eq googleId }.firstOrNull()?.toUser()
        }
    }

    override suspend fun createUser(user: User): User {
        return dbQuery {
            val userEntity = UserEntity.new {
                this.googleId = user.googleId
                this.email = user.email
                this.name = user.name
                this.profilePictureUrl = user.profilePictureUrl
                this.age = user.age
                this.heightCm = user.heightCm
                this.weightGrams = user.weightGrams
                this.gender = user.gender
                this.weightGoal = user.weightGoal
                this.lifestyle = user.lifestyle
                this.dailyCalories = user.dailyCalories
                this.dailyProteins = user.dailyProteins
                this.dailyFats = user.dailyFats
                this.dailyCarbs = user.dailyCarbs
            }
            userEntity.toUser()
        }
    }

    override suspend fun deleteUser(userId: Int) {
        dbQuery {
            UserEntity.findById(userId)?.delete()
        }
    }

}