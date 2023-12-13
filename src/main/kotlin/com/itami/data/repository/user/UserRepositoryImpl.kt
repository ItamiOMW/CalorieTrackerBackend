package com.itami.data.repository.user

import com.itami.data.database.DatabaseFactory.dbQuery
import com.itami.data.database.entity.UserEntity
import com.itami.data.database.table.Users
import com.itami.data.mapper.toUser
import com.itami.data.model.user.CreateUser
import com.itami.data.model.user.User

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

    override suspend fun getUserByEmail(email: String): User? {
        return dbQuery {
            UserEntity.find { Users.email eq email }.firstOrNull()?.toUser()
        }
    }

    override suspend fun createUser(createUser: CreateUser): User {
        return dbQuery {
            val userEntity = UserEntity.new {
                this.googleId = createUser.googleId
                this.email = createUser.email
                this.name = createUser.name
                this.profilePictureUrl = createUser.profilePictureUrl
                this.age = createUser.age
                this.heightCm = createUser.heightCm
                this.weightGrams = createUser.weightGrams
                this.gender = createUser.gender
                this.weightGoal = createUser.weightGoal
                this.lifestyle = createUser.lifestyle
                this.dailyCalories = createUser.dailyCalories
                this.dailyProteins = createUser.dailyProteins
                this.dailyFats = createUser.dailyFats
                this.dailyCarbs = createUser.dailyCarbs
                this.waterMl = createUser.waterMl
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