package com.itami.data.repository.user

import com.itami.data.database.exposed.DatabaseFactory.dbQuery
import com.itami.data.database.exposed.entity.ActivationTokenEntity
import com.itami.data.database.exposed.entity.PasswordResetCodeEntity
import com.itami.data.database.exposed.entity.UserEntity
import com.itami.data.database.exposed.entity.WeightEntity
import com.itami.data.database.exposed.table.ActivationTokens
import com.itami.data.database.exposed.table.PasswordResetCodes
import com.itami.data.database.exposed.table.Users
import com.itami.data.database.exposed.table.Weights
import com.itami.data.mapper.toActivationToken
import com.itami.data.mapper.toPasswordResetCode
import com.itami.data.mapper.toUser
import com.itami.data.mapper.toWeight
import com.itami.data.model.user.*
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.javatime.date
import java.time.LocalDate
import java.time.LocalDateTime

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

    override suspend fun createUserEmail(createUserEmail: CreateUserEmail): User {
        val userId = dbQuery {
            val userEntity = UserEntity.new {
                this.email = createUserEmail.email
                this.hashPassword = createUserEmail.hashPassword
                this.name = createUserEmail.name
                this.profilePictureUrl = createUserEmail.profilePictureUrl
                this.age = createUserEmail.age
                this.heightCm = createUserEmail.heightCm
                this.gender = createUserEmail.gender
                this.weightGoal = createUserEmail.weightGoal
                this.lifestyle = createUserEmail.lifestyle
                this.dailyCalories = createUserEmail.dailyCalories
                this.dailyProteins = createUserEmail.dailyProteins
                this.dailyFats = createUserEmail.dailyFats
                this.dailyCarbs = createUserEmail.dailyCarbs
                this.waterMl = createUserEmail.waterMl
                this.isActive = createUserEmail.isActive
            }
            WeightEntity.new {
                this.weightGrams = createUserEmail.weightGrams
                this.user = userEntity
            }
            userEntity.id.value
        }
        return getUserById(userId) ?: throw Exception()
    }

    override suspend fun createUserGoogle(createUserGoogle: CreateUserGoogle): User {
        val userId = dbQuery {
            val userEntity = UserEntity.new {
                this.googleId = createUserGoogle.googleId
                this.email = createUserGoogle.email
                this.name = createUserGoogle.name
                this.profilePictureUrl = createUserGoogle.profilePictureUrl
                this.age = createUserGoogle.age
                this.heightCm = createUserGoogle.heightCm
                this.gender = createUserGoogle.gender
                this.weightGoal = createUserGoogle.weightGoal
                this.lifestyle = createUserGoogle.lifestyle
                this.dailyCalories = createUserGoogle.dailyCalories
                this.dailyProteins = createUserGoogle.dailyProteins
                this.dailyFats = createUserGoogle.dailyFats
                this.dailyCarbs = createUserGoogle.dailyCarbs
                this.waterMl = createUserGoogle.waterMl
                this.isActive = createUserGoogle.isActive
            }
            WeightEntity.new {
                this.weightGrams = createUserGoogle.weightGrams
                this.user = userEntity
            }
            userEntity.id.value
        }
        return getUserById(userId) ?: throw Exception()
    }

    override suspend fun updateUser(userId: Int, updateUser: UpdateUser): User? {
        return dbQuery {
            UserEntity.findById(userId)?.apply {
                this.googleId = updateUser.googleId
                this.email = updateUser.email
                this.hashPassword = updateUser.hashPassword
                this.name = updateUser.name
                this.profilePictureUrl = updateUser.profilePictureUrl
                this.age = updateUser.age
                this.heightCm = updateUser.heightCm
                this.gender = updateUser.gender
                this.weightGoal = updateUser.weightGoal
                this.lifestyle = updateUser.lifestyle
                this.dailyCalories = updateUser.dailyCalories
                this.dailyProteins = updateUser.dailyProteins
                this.dailyFats = updateUser.dailyFats
                this.dailyCarbs = updateUser.dailyCarbs
                this.waterMl = updateUser.waterMl
                this.isActive = updateUser.isActive
            }?.toUser()
        }
    }

    override suspend fun getActivationToken(token: String): ActivationToken? {
        return dbQuery {
            ActivationTokenEntity.find {
                ActivationTokens.token eq token
            }.firstOrNull()?.toActivationToken()
        }
    }

    override suspend fun saveActivationToken(activationToken: ActivationToken): ActivationToken {
        return dbQuery {
            ActivationTokenEntity.new {
                this.token = activationToken.token
                this.userEmail = activationToken.email
                this.expiresAt = activationToken.expiresAt
            }.toActivationToken()
        }
    }

    override suspend fun deleteActivationToken(token: String) {
        dbQuery {
            ActivationTokenEntity.find {
                ActivationTokens.token eq token
            }.forEach { it.delete() }
        }
    }

    override suspend fun getPasswordResetCode(email: String, code: Int): PasswordResetCode? {
        return dbQuery {
            PasswordResetCodeEntity.find {
                (PasswordResetCodes.code eq code) and (PasswordResetCodes.userEmail eq email)
            }.firstOrNull()?.toPasswordResetCode()
        }
    }

    override suspend fun savePasswordResetCode(passwordResetCode: PasswordResetCode): PasswordResetCode {
        return dbQuery {
            PasswordResetCodeEntity.new {
                this.code = passwordResetCode.code
                this.userEmail = passwordResetCode.email
                this.expiresAt = passwordResetCode.expiresAt
            }.toPasswordResetCode()
        }
    }

    override suspend fun deletePasswordResetCode(code: Int, email: String) {
        dbQuery {
            PasswordResetCodeEntity.find {
                (PasswordResetCodes.code eq code) and (PasswordResetCodes.userEmail eq email)
            }.forEach { it.delete() }
        }
    }

    override suspend fun deleteUser(userId: Int) {
        dbQuery {
            UserEntity.findById(userId)?.delete()
        }
    }

    override suspend fun addWeight(userId: Int, weightGrams: Int, datetime: LocalDateTime): Weight {
        return dbQuery {
            val id = Weights.insertAndGetId { table ->
                table[this.userId] = userId
                table[this.weightGrams] = weightGrams
                table[this.datetime] = datetime
            }
            WeightEntity[id.value].toWeight()
        }
    }

    override suspend fun editWeight(userId: Int, weightId: Int, weightGrams: Int): Weight {
        return dbQuery {
            WeightEntity[weightId].apply {
                this.weightGrams = weightGrams
            }.toWeight()
        }
    }

    override suspend fun getWeights(userId: Int): List<Weight> {
        return dbQuery {
            WeightEntity.find {
                Weights.userId eq userId
            }.map { it.toWeight() }
        }
    }

    override suspend fun getWeightByDate(userId: Int, date: LocalDate): Weight? {
        return dbQuery {
            WeightEntity.find {
                (Weights.userId eq userId) and (Weights.datetime.date() eq date)
            }.firstOrNull()?.toWeight()
        }
    }

    override suspend fun getWeightsBetweenDates(userId: Int, startDate: LocalDate, endDate: LocalDate): List<Weight> {
        return dbQuery {
            WeightEntity.find {
                (Weights.userId eq userId) and
                        (Weights.datetime.date().greaterEq(startDate) and Weights.datetime.date().lessEq(endDate))
            }.map { it.toWeight() }
        }
    }

}