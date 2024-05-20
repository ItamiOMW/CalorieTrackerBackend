package com.itami.data.repository.user

import com.itami.data.database.DatabaseFactory.dbQuery
import com.itami.data.database.entity.UserEntity
import com.itami.data.database.entity.WeightEntity
import com.itami.data.database.table.Users
import com.itami.data.database.table.Weights
import com.itami.data.mapper.toUser
import com.itami.data.mapper.toWeight
import com.itami.data.model.user.CreateUser
import com.itami.data.model.user.User
import com.itami.data.model.user.Weight
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

    override suspend fun createUser(createUser: CreateUser): User {
        val userId = dbQuery {
            val userEntity = UserEntity.new {
                this.googleId = createUser.googleId
                this.email = createUser.email
                this.name = createUser.name
                this.profilePictureUrl = createUser.profilePictureUrl
                this.age = createUser.age
                this.heightCm = createUser.heightCm
                this.gender = createUser.gender
                this.weightGoal = createUser.weightGoal
                this.lifestyle = createUser.lifestyle
                this.dailyCalories = createUser.dailyCalories
                this.dailyProteins = createUser.dailyProteins
                this.dailyFats = createUser.dailyFats
                this.dailyCarbs = createUser.dailyCarbs
                this.waterMl = createUser.waterMl
            }
            WeightEntity.new {
                this.weightGrams = createUser.weightGrams
                this.user = userEntity
            }
            userEntity.id.value
        }
        return getUserById(userId) ?: throw Exception()
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